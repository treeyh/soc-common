package com.treeyh.common.web.filter;

import com.treeyh.common.utils.DateUtils;
import com.treeyh.common.utils.JsonUtils;
import com.treeyh.common.utils.StreamUtils;
import com.treeyh.common.web.context.HttpContext;
import com.treeyh.common.web.context.RequestWrapper;
import com.treeyh.common.web.context.ResponseWrapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author TreeYH
 * @version 1.0
 * @description 打印输入输出日志，通过 R-R-LOG 输出
 * @create 2019-05-20 10:14
 */
@Order(0)
@Component
public class RequestResponseLogFilter extends BaseFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLogFilter.class);
    private static final Logger rrlogger = LoggerFactory.getLogger("R-R-LOG");

    private static final String REQUEST_PREFIX = "request|";
    private static final String RESPONSE_PREFIX = "response|";

    private ThreadLocal<Date> startTimeThread = new ThreadLocal<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        // 记录请求开始时间
        startTimeThread.set(new Date(System.currentTimeMillis()));

        request = new RequestWrapper(request);
        response = new ResponseWrapper(response);

        HttpContext.start(request, response);

        try {
            filterChain.doFilter(request, response);
        } finally {
            rrLogger(request, (ResponseWrapper)response);
        }
    }

    /**
     * 记录request和response日志
     * @param request
     * @param response
     */
    protected void rrLogger(final HttpServletRequest request, final ResponseWrapper response){
        StringBuilder msg = new StringBuilder();

        //构造request日志
        msg.append(REQUEST_PREFIX);
        msg.append("_traceId=").append(HttpContext.getTraceId()).append("|");
        msg.append("start=").append(DateUtils.getDateTime(startTimeThread.get())).append("|");
        msg.append("ip=").append(HttpContext.getIp()).append("|");
        if (request.getContentType() != null) {
            msg.append("content_type=").append(request.getContentType()).append("|");
        }else{
            msg.append("content_type=|");
        }
        msg.append("uri=").append(request.getRequestURI());
        if (request.getQueryString() != null) {
            msg.append('?').append(request.getQueryString());
        }

        if (request instanceof RequestWrapper && !isMultipart(request) && !isBinaryContent(request)) {
            RequestWrapper requestWrapper = (RequestWrapper) request;
            try {
                String charEncoding = requestWrapper.getCharacterEncoding() != null ?
                        requestWrapper.getCharacterEncoding() : "UTF-8";
                String body = StreamUtils.getStringByStream(requestWrapper.getInputStream(), charEncoding);
                msg.append("|body=").append(body);
                if("POST".equals(requestWrapper.getMethod().toUpperCase()) && StringUtils.isEmpty(body)){
                    msg.append(JsonUtils.toJson(request.getParameterMap()));
                }
            } catch (IOException e) {
                logger.error("_traceId=" + HttpContext.getTraceId() + "Failed to parse request payload", e);
            }
        }else{
            msg.append("|body=");
        }

        //构造response日志
        msg.append("]----[").append(RESPONSE_PREFIX);
        msg.append("_traceId=").append(HttpContext.getTraceId()).append("|");
        msg.append("end=").append(DateUtils.getCurrentTime()).append("|");
        msg.append("time=").append(HttpContext.finish()).append("|");
        msg.append("status=").append(HttpContext.getResponse().getStatus());
        try {
            msg.append("|body=").append(new String(response.toByteArray(), response.getCharacterEncoding()));
        } catch (UnsupportedEncodingException e) {
            logger.error("_traceId=" + HttpContext.getTraceId() + "Failed to parse response payload", e);
        }


        rrlogger.info(msg.toString());
    }


    /**
     * 判断是否是二进制内容
     * @param request
     * @return
     */
    private boolean isBinaryContent(final HttpServletRequest request) {
        if (request.getContentType() == null) {
            return false;
        }
        return request.getContentType().startsWith("image") || request.getContentType().startsWith("video")
                || request.getContentType().startsWith("audio");
    }


    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith("multipart/form-data");
    }

}