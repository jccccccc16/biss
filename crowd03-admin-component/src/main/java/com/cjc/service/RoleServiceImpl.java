package com.cjc.service;


import com.cjc.crowd.entity.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.cjc.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public PageInfo<Role> getRole(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleMapper.selectRole(keyword);
        return new  PageInfo<Role>(roleList);
    }

    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
}
