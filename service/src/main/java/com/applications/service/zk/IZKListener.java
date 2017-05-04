package com.applications.service.zk;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author hukaisheng
 * @date 2017/4/17.
 */
public interface IZKListener {

    void executor(CuratorFramework client);
}
