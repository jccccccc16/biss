package com.cjc.mvc.controller;

import com.cjc.crowd.entity.Menu;
import com.cjc.service.MenuService;
import com.cjc.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MenuController {
    @Resource
    private MenuService menuService;

    private Logger logger = LoggerFactory.getLogger(MenuController.class);


    @ResponseBody
    @RequestMapping("/menu/get/menu/tree.json")
    public ResultEntity<Menu> getMenuTree(){

        List<Menu> menus = menuService.getAll();

        Menu root = null; // 存放根菜单

        Map<Integer, Menu> menuMap = new HashMap<Integer, Menu>();
        for (Menu menu : menus) {
            Integer id = menu.getId();
            menuMap.put(id,menu);
        }

        for (Menu menu : menus) {
            Integer pid = menu.getPid();
            if(pid==null){ // 根节点
                root = menu;
                continue;
            }
            Menu father = menuMap.get(pid); // 获取父节点
            father.getChildren().add(menu);
        }
        logger.info("查询菜单数据:"+root);
        return ResultEntity.successWithData(root);
    }

    @RequestMapping("/save/menu.json")
    @ResponseBody
    public ResultEntity<String> saveMenu(@RequestBody Menu menu){
        logger.info("接收到:"+menu.getName());
        int i = menuService.saveMenu(menu);
        if(i==0){
            return ResultEntity.failed("添加菜单失败！");
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/edit/menu/by/id.json")
    @ResponseBody
    public ResultEntity<String> editMenu(@RequestBody Menu menu){
        logger.info("接收到:"+menu.getName());
        int i = menuService.update(menu);
        if(i==0){
            return ResultEntity.failed("修改菜单失败！");
        }
        return ResultEntity.successWithoutData();
    }



    @RequestMapping("/remove/menu/by/id.json")
    @ResponseBody
    public ResultEntity<String> removeMenu(@RequestParam("id")int id){
        int i = menuService.deleteById(id);
        if(i==0){
            return ResultEntity.failed("删除菜单失败！");
        }
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/menu/get/menu/to/side/bar.json")
    @ResponseBody
    public ResultEntity<List<Menu>> getSideBarMenu(){
        List<Menu> allMenus = menuService.getAll();
        allMenus.remove(0);
        Map<Integer, List<Menu>> collect = allMenus.stream().collect(Collectors.groupingBy(Menu::getPid));
        // 获取二级菜单
        List<Menu> menus = collect.get(1);
        List<Menu> resultMenu = new ArrayList<>();
        for (Menu menu : menus) {
            List<Menu> childrenNode = collect.get(menu.getId()); // 获取二级菜单的孩子节点
            menu.setChildren(childrenNode);
            resultMenu.add(menu);
        }
        return ResultEntity.successWithData(resultMenu);
    }



}
