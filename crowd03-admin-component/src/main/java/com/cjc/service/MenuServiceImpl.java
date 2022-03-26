package com.cjc.service;

import com.cjc.crowd.entity.Menu;
import com.cjc.crowd.entity.MenuExample;
import com.cjc.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    public List<Menu> getAll() {
        List<Menu> menus = menuMapper.selectByExample(new MenuExample());
        return menus;
    }

    public int saveMenu(Menu menu) {
        return menuMapper.insertSelective(menu);
    }

    public int update(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    public int deleteById(int id) {
        return menuMapper.deleteByPrimaryKey(id);
    }
}
