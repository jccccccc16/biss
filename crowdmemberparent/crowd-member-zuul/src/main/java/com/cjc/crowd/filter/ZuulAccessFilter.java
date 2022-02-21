package com.cjc.crowd.filter;

import com.cjc.crowd.util.constant.AccessPassResources;
import com.cjc.crowd.util.constant.CrowdConstant;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录检查过滤器
 */
@Component
public class ZuulAccessFilter extends ZuulFilter {

    /**
     * pre过滤器类型
     * @return
     */
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    private Logger logger = LoggerFactory.getLogger(ZuulAccessFilter.class);



    /**
     * 过滤逻辑
     * @return
     */
    public boolean shouldFilter() {

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request =
                currentContext.getRequest();
        String servletPath = request.getServletPath();
        logger.info("servletPath: "+servletPath);
        // 判断是否可以放行
        boolean isPass = AccessPassResources.PASS_RES_SET.contains(servletPath);
        if(isPass){
            // 放行
            return false;
        }
        // 判断是否是静态资源，如果不是那么就执行登录检查，也就是run方法
        return !AccessPassResources.judgeStaticResource(servletPath);
    }

    /**
     * 检查是否已经登录
     * @return
     * @throws ZuulException
     */
    public Object run() throws ZuulException {
        // 获取session
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        Object loginMember = session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
        if(loginMember==null){
            // 如果为空那么，返回登录页面并且返回提示信息
            HttpServletResponse response = currentContext.getResponse();
            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            try {
                logger.info("没有登录，被拦截");
                // 重定向到登录页面
                response.sendRedirect("http://localhost/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
