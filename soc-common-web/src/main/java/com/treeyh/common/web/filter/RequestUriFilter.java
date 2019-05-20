package com.treeyh.common.web.filter;

import com.treeyh.common.constants.SocCommonConstans;
import com.treeyh.common.model.result.ResultCode;
import com.treeyh.common.model.result.ReturnResult;
import com.treeyh.common.utils.JsonUtils;
import com.treeyh.common.web.SocCommonWebConfig;
import com.treeyh.common.web.context.HttpContext;
import com.treeyh.common.web.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author TreeYH
 * @version 1.0
 * @description 心跳支持，访问URI拦截，目前只做非回调接口的外网拦截。
 * @create 2019-05-20 10:16
 */
@Order(10)
@Component
public class RequestUriFilter extends BaseFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestUriFilter.class);

    @Autowired
    private SocCommonWebConfig webConfig;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        //获取相对URI
        String requestURI = httpServletRequest.getRequestURI();

        if("/checkHealth".equals(requestURI)){
            //心跳检测,直接返回
            if(SocCommonWebConfig.SERVICE_RUN_STATUS.equals(SocCommonWebConfig.SERVICE_RUN_STATUS_STARTED)){
                //判断应用当前是否为不可用状态，如果是那么返回心跳 406 消息，从便于负载均衡摘除
                this.responseEnd(httpServletResponse, HttpStatus.NOT_ACCEPTABLE.value(), "406");
                return;
            }

            this.responseEnd(httpServletResponse, "OK");
            return;
        }


        if(!webConfig.getInnerIpType().equals(0)) {
            String fromIP = IpUtils.getRequestIpAddress(httpServletRequest);
            // 仅内网访问接口
            if (!IpUtils.isInnerIP(fromIP) && webConfig.isInternetAccessUrl(requestURI)) {
                //判断ip是否为外网
                logger.info("_traceId=" + HttpContext.getTraceId() + " request ip not access. ip:" +
                        fromIP + "; requestUrl:" + requestURI);

                this.responseEnd(httpServletResponse, JsonUtils.toJson(ReturnResult.error(ResultCode.FORBIDDEN_ACCESS)));
                return;
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }


}
