package com.cjc.service;


import com.cjc.crowd.entity.Admin;
import com.cjc.crowd.entity.vo.AdminEditView;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {
    /**
     * 查找管理员
     * @param keyword  查询条件
     * @param pageNum 页码
     * @param pageSize 每一页的数据大小，
     * @return
     */
    public PageInfo<Admin> findAll(String keyword,Integer pageNum,Integer pageSize);

    /**
     * 查找账号
     * @param loginAcct
     * @param userPswd
     * @return
     */
    Admin getAdminByLoginAcct(String loginAcct,String userPswd);


    void saveAdmin(Admin admin);

    Admin getAdminById(Integer adminId);

    int updateAdmin(AdminEditView adminEditView);
}
