package com.applications.service.zk.impl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.applications.service.zk.IZKListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.LoggerFactory;


/**
 * @author hukaisheng
 * @date 2017/4/17.
 */
@Slf4j
public class LogbackLevelListener implements IZKListener {

    private String path;

    //Logback日志级别ZNode
    public LogbackLevelListener(String path) {
        this.path = path;
    }

    @Override
    public void executor(CuratorFramework client) {

        //使用Curator的NodeCache来做ZNode的监听，不用我们自己实现重复监听
        final NodeCache cache = new NodeCache(client, path);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {

                byte[] data = cache.getCurrentData().getData();

                //设置日志级别
                if (data != null) {
                    String level = new String(data);
                    Logger logger = (Logger) LoggerFactory.getLogger("root");
                    Level newLevel = Level.fromLocationAwareLoggerInteger(Integer.parseInt(level));
                    logger.setLevel(newLevel);
                    System.out.println("Setting logback new level to :" + newLevel.levelStr);
                }
            }
        });
        try {
            cache.start(true);
        } catch (Exception e) {
            log.error("Start NodeCache error for path: {}, error info: {}", path, e.getMessage());
        }
    }
}
