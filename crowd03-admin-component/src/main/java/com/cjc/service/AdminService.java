package com.cjc.service;


import com.cjc.crowd.entity.Admin;

import java.util.List;

public interface AdminService {
    public List<Admin> findAll();

    /**
     * 查找账号
     * @param loginAcct
     * @param userPswd
     * @return
     */
    Admin getAdminByLoginAcct(String loginAcct,String userPswd);
}
