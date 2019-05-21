package com.treeyh.common.web.filter;

import com.treeyh.common.constants.SocCommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TreeYH
 * @version 1.0
 * @description Filter 基类
 * @create 2019-05-17 19:27
 */
public abstract class BaseFilter extends OncePerRequestFilter {

    protected void responseEnd(HttpServletResponse response, String msg) throws IOException {
        this.responseEnd(response, HttpStatus.OK.value(), msg);
    }

    protected void responseEnd(HttpServletResponse response, Integer httpStatus, String msg) throws IOException {
        response.setCharacterEncoding(SocCommonConstants.UTF8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(httpStatus);
        response.getWriter().append(msg).flush();
    }
}
