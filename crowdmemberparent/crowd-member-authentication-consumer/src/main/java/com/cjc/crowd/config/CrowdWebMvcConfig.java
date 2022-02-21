package com.cjc.crowd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {

    /**
     * 解决刷新页面session失效
     * @return
     */
    @Bean
    public CookieSerializer httpSessionIdResolver() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        // 取消仅限同一站点设置
        cookieSerializer.setSameSite(null);
        return cookieSerializer;
    }

    public void addViewControllers(ViewControllerRegistry registry) {
//         我的众筹
        registry.addViewController("/member/my/crowd")
                .setViewName("member-crowd");
        registry.addViewController("/auth/member/to/login/page")
                .setViewName("member-login");  // 登录页面
        registry.addViewController("/")
                .setViewName("portal");// 首页
        registry.addViewController("/index.html")
                .setViewName("portal");// 首页
        registry.addViewController("/auth/member/to/reg/page")
                .setViewName("member-reg");
//        registry.addViewController("/member/to/center/page")
//                .setViewName("member-crowd");

    }


}
