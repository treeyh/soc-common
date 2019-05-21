package com.treeyh.common.web.listener;

import com.treeyh.common.constants.SocCommonConstants;
import com.treeyh.common.web.SocCommonWebConfig;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TreeYH
 * @version 1.0
 * @description 描述
 * @create 2019-05-20 10:22
 */
@Component
public class GracefulShutdownListener implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutdownListener.class);

    private volatile Connector connector;

    @Autowired
    private SocCommonWebConfig webConfig;

    private static final Integer waitTime = 5;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        logger.info("Application end...");

        if(SocCommonWebConfig.SERVICE_RUN_STATUS.equals(SocCommonWebConfig.SERVICE_RUN_STATUS_NOT_WEB_SYS)){
            logger.info("Application end over");
            return;
        }

        //设置应用状态为准备停止
        SocCommonWebConfig.SERVICE_RUN_STATUS = SocCommonWebConfig.SERVICE_RUN_STATUS_STOPPING;
        try {
            //sleep一段时间供负载均衡心跳捕获
            Thread.sleep(webConfig.getStopWaitTime() * SocCommonConstants.MILLISECOND_UNIT);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        if(null != this.connector) {
            this.connector.pause();
            Executor executor = this.connector.getProtocolHandler().getExecutor();
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    threadPoolExecutor.shutdown();
                    if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
                        logger.warn("Tomcat 进程在" + waitTime + " 秒内无法结束，尝试强制结束");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        logger.info("Application end over");
    }
}