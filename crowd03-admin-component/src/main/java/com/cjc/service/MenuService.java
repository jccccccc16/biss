package com.cjc.service;


import com.cjc.crowd.entity.Menu;

import java.util.List;

public interface MenuService {
    public List<Menu> getAll();

    int saveMenu(Menu menu);

    int update(Menu menu);

    int deleteById(int id);
}
