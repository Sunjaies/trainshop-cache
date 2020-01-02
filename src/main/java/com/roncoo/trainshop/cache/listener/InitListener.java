package com.roncoo.trainshop.cache.listener;

import com.roncoo.trainshop.cache.kafka.KafkaConsumer;
import com.roncoo.trainshop.cache.rebuild.RebuildCacheThread;
import com.roncoo.trainshop.cache.spring.SpringContext;
import com.roncoo.trainshop.cache.zk.ZooKeeperSession;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统初始化的监听器
 *
 * @author Administrator
 */
public class InitListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
        SpringContext.setApplicationContext(context);

        new Thread(new KafkaConsumer("cache-message")).start();
        new Thread(new RebuildCacheThread()).start();

        ZooKeeperSession.init();
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
