package com.applications.service.distribute.chapter05.$5_4_1;
import org.I0Itec.zkclient.ZkClient;

// 使用ZkClient创建节点
public class Create_Node_Sample {

    public static void main(String[] args) throws Exception {
    	ZkClient zkClient = new ZkClient("domain1.distribute.zookeeper:2181", 5000);
        String path = "/zk-distribute/c1";
        zkClient.createPersistent(path, true);
    }
}