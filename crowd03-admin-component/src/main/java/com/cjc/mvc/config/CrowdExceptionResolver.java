package com.cjc.mvc.config;

import com.cjc.util.CrowdUtil;
import com.cjc.util.ResultEntity;
import com.cjc.util.constant.CrowdConstant;
import com.cjc.util.exception.LoginFailedException;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 异常映射处理
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    @ExceptionHandler(value = LoginFailedException.class)
    public ModelAndView resolveLoginFailedException(
            LoginFailedException exception,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String viewName = "admin-login";

        return commonResolveException(exception, request, response, viewName);

    }


    /**
     * 处理普通请求和ajax请求
     *
     * @param exception
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    private ModelAndView commonResolveException(Exception exception, HttpServletRequest request, HttpServletResponse response, String viewName) throws IOException {
        boolean requestType = CrowdUtil.judgeRequestType(request);
        // 如果是ajax请求
        if (requestType) {
            String message = exception.getMessage();
            ResultEntity<Object> resultEntity = ResultEntity.failed(message);
            Gson gson = new Gson();
            String json = gson.toJson(resultEntity);
            PrintWriter writer = response.getWriter();
            writer.write(json);
            return null;
        }

        // 如果是普通请求
        ModelAndView modelAndView = new ModelAndView();
        // 存入exception
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION, exception);
        // 设置返回页面
        modelAndView.setViewName(viewName);

        return modelAndView;


    }


}
