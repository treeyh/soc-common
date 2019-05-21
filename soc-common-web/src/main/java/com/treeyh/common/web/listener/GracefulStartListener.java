package com.treeyh.common.web.listener;

import com.treeyh.common.constants.SocCommonConstants;
import com.treeyh.common.web.SocCommonWebConfig;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author TreeYH
 * @version 1.0
 * @description 描述
 * @create 2019-05-20 14:43
 */
@Component
public class GracefulStartListener implements TomcatConnectorCustomizer, ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GracefulStartListener.class);

    private volatile Connector connector;

    @Autowired
    private SocCommonWebConfig webConfig;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Application start...");

        if(!WebApplicationType.SERVLET.equals(applicationReadyEvent.getSpringApplication().getWebApplicationType())){
            SocCommonWebConfig.SERVICE_RUN_STATUS = SocCommonWebConfig.SERVICE_RUN_STATUS_NOT_WEB_SYS;
            logger.info("Application start over.");
            return;
        }

        //设置5秒的启动缓冲接入时间
        try{
            Thread.sleep(webConfig.getStartWaitTime() * SocCommonConstants.MILLISECOND_UNIT);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        //设置应用状态为可用
        SocCommonWebConfig.SERVICE_RUN_STATUS = SocCommonWebConfig.SERVICE_RUN_STATUS_STARTED;

        logger.info("Application start over.");

        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(applicationReadyEvent.getApplicationContext().getBean(GracefulShutdownListener.class));

        logger.info("Application add shutdown listener over.");
    }
}