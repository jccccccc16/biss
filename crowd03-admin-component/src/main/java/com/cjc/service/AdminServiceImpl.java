package com.cjc.service;

import com.cjc.util.constant.CrowdConstant;
import com.cjc.util.exception.LoginAcctDuplicateException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.AdminMapper;
import com.cjc.crowd.entity.Admin;
import com.cjc.crowd.entity.AdminExample;
import com.cjc.util.CrowdUtil;
import com.cjc.util.exception.LoginFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    public PageInfo<Admin> findAll(String keyword,Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Admin> adminList = adminMapper.selectAdmin(keyword);
        PageInfo<Admin> adminPageInfo = new PageInfo<Admin>(adminList);
        return adminPageInfo;
    }

    public Admin getAdminByLoginAcct(String loginAcct,String userPswd) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        // 判断是否为空，如果为空，那么就是不存在该账号，输入账号错误
        if(adminList==null ||adminList.size()==0){
            // 抛出登录失败异常
            logger.warn("账号错误");
            throw new LoginFailedException();
        }
        // 比对密码
        String passwordEncoded = CrowdUtil.md5(userPswd);
        //数据库中对象的密码
        Admin admin = adminList.get(0);
        String passwordEncodedFromDB = admin.getUserPswd();
        // 如果不匹配
        if(!passwordEncodedFromDB.equals(passwordEncoded)){
            logger.warn("密码错误");
            throw new LoginFailedException();
        }
        // 账号密码正确
        // 将登录对象密码置空
        admin.setUserPswd(null);
        return admin;
    }

    public void saveAdmin(Admin admin)  {

        admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
        admin.setCreateTime(CrowdUtil.getNow(CrowdConstant.DATE_PATTERN_01));
        try{
            adminMapper.insert(admin);
            // 账号重复异常
        }catch (Exception e){
            e.printStackTrace();
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctDuplicateException();
            }

        }
    }
}
