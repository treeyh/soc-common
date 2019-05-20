package com.treeyh.common.web.filter;

import com.treeyh.common.constants.SocCommonConstans;
import org.springframework.http.HttpStatus;
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
        response.setCharacterEncoding(SocCommonConstans.UTF8);
        response.setContentType(SocCommonConstans.HEADER_CONTENT_TYPE);
        response.setStatus(httpStatus);
        response.getWriter().append(msg).flush();
    }
}
