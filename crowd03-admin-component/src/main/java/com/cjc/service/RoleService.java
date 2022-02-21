package com.cjc.service;

import com.cjc.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

   PageInfo<Role> getRole(String keyword,Integer pageNum,Integer pageSize);

    void saveRole(Role role);

    void updateRole(Role role);
}
