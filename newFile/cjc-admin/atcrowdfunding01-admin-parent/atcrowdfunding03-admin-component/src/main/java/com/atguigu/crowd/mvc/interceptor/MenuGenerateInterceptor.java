package com.atguigu.crowd.mvc.interceptor;



import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.service.api.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单拦截器，旨在每次浏览页面都动态生成菜单
 */
@Component
public class MenuGenerateInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MenuService menuService;
    private Logger logger = LoggerFactory.getLogger(MenuGenerateInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        Object menus = session.getAttribute("menus");
        logger.info("菜单拦截器");
//        if(menus==null){
//            logger.info("menus为空");
            //生成menus
            List<Menu> allMenus = menuService.getAll();
            allMenus.remove(0);
            Map<Integer, List<Menu>> collect = allMenus.stream().collect(Collectors.groupingBy(Menu::getPid));
            // 获取二级菜单
            List<Menu> menusFromMysql = collect.get(1);
            List<Menu> resultMenuList = new ArrayList<>();
            for (Menu menu : menusFromMysql) {
                List<Menu> childrenNode = collect.get(menu.getId()); // 获取二级菜单的孩子节点
                menu.setChildren(childrenNode);
                resultMenuList.add(menu);
            }
            session.setAttribute("menus",resultMenuList);
//        }
    }
}
