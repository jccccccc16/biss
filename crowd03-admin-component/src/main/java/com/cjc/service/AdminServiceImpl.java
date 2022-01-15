package com.cjc.service;

import com.cjc.mapper.AdminMapper;
import com.cjc.crowd.entity.Admin;
import com.cjc.crowd.entity.AdminExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public List<Admin> findAll() {
        List<Admin> admins = adminMapper.selectByExample(new AdminExample());
        return null;
    }
}
