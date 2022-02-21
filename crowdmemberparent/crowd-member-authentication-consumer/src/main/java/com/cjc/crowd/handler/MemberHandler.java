package com.cjc.crowd.handler;

import com.cjc.crowd.api.MySqlRemoteService;
import com.cjc.crowd.api.RedisRemoteService;
import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.po.ProjectPO;
import com.cjc.crowd.entity.vo.MemberVO;
import com.cjc.crowd.entity.vo.MyCrowdProjectVO;
import com.cjc.crowd.util.CrowdUtil;
import com.cjc.crowd.util.ResultEntity;
import com.cjc.crowd.util.constant.CrowdConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {
    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private JavaMailSender javaMailSender;
    private Logger logger = LoggerFactory.getLogger(MemberHandler.class);
    @RequestMapping("/auth/member/do/logout")
    public String logout(HttpSession session){
        session.invalidate();
        session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGOUT_SUCCESS);
        logger.info("退出登录成功");
        return "member-login";
    }

    @RequestMapping("/auth/member/do/login")
    public String login(
            @RequestParam("loginacct") String loginacct,
            @RequestParam("userpswd") String userpswd,
            ModelMap modelMap,
            HttpSession session
    ) {

        ResultEntity<Member> resultEntity
                = mySqlRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        Member memberMysql = resultEntity.getData();
        // 调用失败，那么返回登录页面
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
            return "member-login";
        }


        // 如果没有找到该用户
        if(memberMysql==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            // 返回登录页
            return "member-login";
        }
        // 获取加密器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 匹配密码
        String userpswdMysql = memberMysql.getUserpswd();
        boolean matches = passwordEncoder.matches(userpswd, userpswdMysql);
        if(matches){
            // 如果匹配
            // 将用户信息加入session中
            session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER,memberMysql);
//            session.setMaxInactiveInterval(900); // 设置15分钟没有活动，销毁session
            // 返回主页面
            return "redirect:http://localhost/";
        }
        // 如果不匹配,返回错误信息
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
        return "member-login";
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping("/auth/reg/send/code.json")
    public ResultEntity<String> sendMessage(@RequestParam("receiver")String receiver){
        try{
            CrowdUtil.MyMessage message = CrowdUtil.getMessage(receiver);
            javaMailSender.send(message.getMessage());
            String key = CrowdConstant.ATTR_NAME_CODE_PREFIX+receiver;
            // 存入redis
            redisRemoteService.setRedisKeyValueRemoteWhiteTimeout(key,message.getCode(),15, TimeUnit.MINUTES);
            logger.info("发送验证码成功");
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed("发送验证码失败，请重试");
        }
    }

    /**
     * 注册
     * @return
     */
    @RequestMapping("/auth/member/do/reg")
    public String reg(MemberVO memberVO,ModelMap modelMap){
        String code = memberVO.getCode();
        String key = CrowdConstant.ATTR_NAME_CODE_PREFIX+memberVO.getEmail();
        ResultEntity<String> codeResultEntity;
        try{
            codeResultEntity= redisRemoteService.getRedisStringValueByKeyRemote(key);
        }catch (Exception e){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,e.getMessage());
            return "member-reg";
        }
        // 判断验证码是否为空
        if(codeResultEntity.getData()==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXIST);
            return "member-reg";
        }
        // 如果验证码匹配
        if(codeResultEntity.getData().equals(code)){
            // 账号是否已存在
            ResultEntity<Member> resultEntity = mySqlRemoteService.getMemberPOByLoginAcctRemote(memberVO.getLoginacct());
            if(resultEntity.getData()!=null){
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_ACCOUNT_DUPLICATE);
                return "member-reg";
            }
            // 将用户存入数据库
            Member memberPersist = new Member();
            BeanUtils.copyProperties(memberVO,memberPersist);
            memberPersist.setUsername(memberPersist.getLoginacct());
            memberPersist.setUserpswd(new BCryptPasswordEncoder().encode(memberPersist.getUserpswd()));
            try{
                mySqlRemoteService.saveMemberpo(memberPersist);
                // 注册成功后，重定向到登录页面
                logger.info("注册成功!");
                return "redirect:http://localhost/auth/member/to/login/page";
            }catch (Exception e){
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,e.getMessage());
                return "member-reg";
            }

        }
        // 如果不匹配
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALIDATE);
        return "member-reg";
    }


    @RequestMapping("/member/get/my/launch.json")
    @ResponseBody
    public ResultEntity<List<MyCrowdProjectVO>> toCrowdPage(
            HttpSession session
    ){
        logger.info("获取我的众筹项目");
         Member loginMember = (Member) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = loginMember.getId();
        ResultEntity<List<ProjectPO>> projectsResultEntity =
                mySqlRemoteService.getProjectByMemberId(memberId);
        List<MyCrowdProjectVO> myCrowdProjectVOList = new ArrayList<MyCrowdProjectVO>();
        List<ProjectPO> projectPOList = projectsResultEntity.getData();
        for (ProjectPO projectPO : projectPOList) {
            MyCrowdProjectVO myCrowdProjectVO = new MyCrowdProjectVO();
            myCrowdProjectVO.setProjectName(projectPO.getProjectName());
            // 众筹资金
            myCrowdProjectVO.setMoney(projectPO.getMoney());
            // 剩余天数
            myCrowdProjectVO.setRemainDay(CrowdUtil.getDateSub(projectPO.getDay(),projectPO.getCreatedate()));
            // 状态
            myCrowdProjectVO.setStatus(projectPO.getStatus());
            // 进度
            double schedule = ((1.0*projectPO.getSupportmoney())/projectPO.getMoney())*100L;
            logger.info(""+schedule);
            myCrowdProjectVO.setSchedule(schedule);
            myCrowdProjectVOList.add(myCrowdProjectVO);
            logger.info(myCrowdProjectVO.toString());
        }

        return ResultEntity.successWithData(myCrowdProjectVOList);
    }



}
