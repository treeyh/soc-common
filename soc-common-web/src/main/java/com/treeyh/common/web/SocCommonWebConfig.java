package com.treeyh.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TreeYH
 * @version 1.0
 * @description 模块配置
 * @create 2019-05-20 11:50
 */
@Component
public class SocCommonWebConfig {


    /**
     * 是否开启内网检测，1开启，0不开启，默认不开启内网访问检测
     */
    @Value("${soc.common.web.filter.request.inner-ip-type:0}")
    private Integer innerIpType;

    /**
     * 允许外网访问的url前缀列表，半角逗号分割，不填则不允许外网访问，默认不开启内网访问检测，例如：/api/callCenter/callback/
     */
    @Value("${soc.common.web.filter.request.internet-ip-urls:}")
    private String internetIpUrls;

    /**
     * 允许外网访问的url列表
     */
    private List<String> internetAccessUrlList;


    /**
     * 启动后正常服务等待时间，单位：秒，默认5秒
     */
    @Value("${soc.common.web.start-wait-time:5}")
    private Integer startWaitTime;

    /**
     * 停止延迟等待时间，单位：秒，默认25秒
     */
    @Value("${soc.common.web.stop-wait-time:25}")
    private Integer stopWaitTime;


    /**
     * 服务正在启动中，不可用
     */
    public static final Integer SERVICE_RUN_STATUS_STARTING = 0;

    /**
     * 服务启动完毕，可以接收请求；
     */
    public static final Integer SERVICE_RUN_STATUS_STARTED = 1;

    /**
     * 服务准备关闭，不接收新请求；
     */
    public static final Integer SERVICE_RUN_STATUS_STOPPING = 2;

    /**
     * 非web应用
     */
    public static final Integer SERVICE_RUN_STATUS_NOT_WEB_SYS = 3;

    /**
     * 服务运行状态：
     * 0：服务正在启动中，不可用；
     * 1：服务启动完毕，可以接收请求；
     * 2：服务准备关闭，不接收新请求；
     * 3:非web应用
     */
    public static volatile Integer SERVICE_RUN_STATUS = SocCommonWebConfig.SERVICE_RUN_STATUS_STARTING;



    public Integer getInnerIpType() {
        return innerIpType;
    }

    public String getInternetIpUrls() {
        return internetIpUrls;
    }

    public Integer getStartWaitTime() {
        return startWaitTime;
    }

    public Integer getStopWaitTime() {
        return stopWaitTime;
    }

    /**
     * 判断是否是外网访问url
     *
     * @param urlPath
     * @return
     */
    public boolean isInternetAccessUrl(String urlPath) {

        if (null == internetAccessUrlList) {
            loadInternetAccessUrlList();
        }
        for (String path : internetAccessUrlList) {
            if (urlPath.startsWith(path)) {
                return true;
            }
        }

        return false;
    }

    private synchronized List<String> loadInternetAccessUrlList() {

        if (null == internetAccessUrlList) {
            internetAccessUrlList = new ArrayList<>();
            String[] ls = internetIpUrls.split(",");
            for (String l : ls) {
                internetAccessUrlList.add(l.trim());
            }
        }
        return internetAccessUrlList;
    }
}
