package com.cjc.crowd.handler;

import com.cjc.crowd.api.MySqlRemoteService;
import com.cjc.crowd.config.OSSProperties;
import com.cjc.crowd.entity.po.Member;
import com.cjc.crowd.entity.vo.MemberConfirmInfoVO;
import com.cjc.crowd.entity.vo.ProjectVO;
import com.cjc.crowd.entity.vo.ReturnVO;
import com.cjc.crowd.util.CrowdUtil;
import com.cjc.crowd.util.ResultEntity;
import com.cjc.crowd.util.constant.CrowdConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectHandler {
    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    private Logger logger = LoggerFactory.getLogger(ProjectHandler.class);

    @RequestMapping("/save/return/picture.json")
    @ResponseBody
    public ResultEntity saveReturnPicture(
            @RequestParam("returnPicture") MultipartFile returnPicture) throws IOException {
        try{
            ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    returnPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    returnPicture.getOriginalFilename());
            if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){
                String picturePath = resultEntity.getData();
                return ResultEntity.successWithData(picturePath);
            }
            return ResultEntity.failed(CrowdConstant.MESSAGE_PIC_UPLOAD_FAIL);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(CrowdConstant.MESSAGE_PIC_UPLOAD_FAIL);
        }

    }


    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn(
            ReturnVO returnVO,
            HttpSession httpSession
    ){
        ProjectVO projectVO =
                (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        // projectvo????????????
        if(projectVO==null){
            return ResultEntity.failed(CrowdConstant.MESSAGE_PROJECT_MISSING);
        }
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        // returnvolist????????????
        if(returnVOList==null || returnVOList.size()==0){
             returnVOList = new ArrayList<ReturnVO>();
             projectVO.setReturnVOList(returnVOList);
        }
        // ??????returnvo
        returnVOList.add(returnVO);
        httpSession.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return ResultEntity.successWithoutData();

    }

    @RequestMapping("/create/project/information")
    public String saveProject(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,
            HttpSession session,
            ModelMap modelMap
    ){

        logger.info(projectVO.toString());
        // ????????????
        boolean isEmpty = headerPicture.isEmpty();
        if(isEmpty){
            modelMap.addAttribute(
                    CrowdConstant.ATTR_NAME_MESSAGE,
                    CrowdConstant.MESSAGE_HEADER_PIC_EMPTY
            );
            return "project-launch";
        }

        // ????????????
        ResultEntity<String> uploadHeaderPicResultEntity=null;
        try {
            uploadHeaderPicResultEntity = CrowdUtil.uploadFileToOss(
                    ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    headerPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    headerPicture.getOriginalFilename()
            );
            if(ResultEntity.SUCCESS.equals(uploadHeaderPicResultEntity.getResult())){
                // ??????????????????,??????????????????
                String headerPicPath = uploadHeaderPicResultEntity.getData();

                projectVO.setHeaderPicturePath(headerPicPath);
            }else{
                // ???????????????????????????????????????
                modelMap.addAttribute(
                        CrowdConstant.ATTR_NAME_MESSAGE,
                        CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAIL
                );
                return "project-launch";
            }
        } catch (IOException e) {
            e.printStackTrace();
            // ???????????????????????????????????????
            modelMap.addAttribute(
                    CrowdConstant.ATTR_NAME_MESSAGE,
                    CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAIL
            );
            return "project-launch";
        }

        List<String> detailPicturePathList = new ArrayList<String>();
        // ???????????????????????????
        if(detailPictureList==null||detailPictureList.size()==0){

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE
                    ,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }

        // ????????????????????????
        for (MultipartFile detailPicture : detailPictureList) {
            // ???????????????????????????
            if(detailPicture.isEmpty()){

                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE
                        ,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            // ??????
            try {
                ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                        ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        detailPicture.getInputStream(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        detailPicture.getOriginalFilename()
                );
                // ????????????????????????
                if(ResultEntity.SUCCESS.equals(resultEntity.getResult())){

                    // ????????????
                    String picPath = resultEntity.getData();
                    detailPicturePathList.add(picPath);
                }else{
                    modelMap.addAttribute(
                            CrowdConstant.ATTR_NAME_MESSAGE,
                            CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAIL
                    );
                    return "project-launch";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // ?????????????????????project???
        projectVO.setDetailPicturePathList(detailPicturePathList);
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return "redirect:http://localhost/project/return/info/page";

    }


    @RequestMapping("/create/confirm")
    public String saveConfirm(ModelMap modelMap, HttpSession session, MemberConfirmInfoVO memberConfirmInfoVO){
        ProjectVO
                projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        if(projectVO==null){
            throw new RuntimeException(CrowdConstant.MESSAGE_PROJECT_MISSING);
        }
        projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
        Member memberLogin = (Member) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer memberId = memberLogin.getId();

        // ????????????
        ResultEntity<String> resultEntity = mySqlRemoteService.saveProjectVORemote(projectVO, memberId);

        // ???????????????????????????????????????
        if(ResultEntity.FAILED.equals(resultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "project-confirm";
        }
        // ??????project
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
        return "redirect:http://localhost/project/create/success";
    }

}
