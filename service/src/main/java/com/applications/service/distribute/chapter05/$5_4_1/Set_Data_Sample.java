package com.applications.service.distribute.chapter05.$5_4_1;

import org.I0Itec.zkclient.ZkClient;

//ZkClient更新节点数据
public class Set_Data_Sample {

    public static void main(String[] args) throws Exception {
    	String path = "/zk-distribute";
    	ZkClient zkClient = new ZkClient("domain1.distribute.zookeeper:2181", 2000);
        zkClient.createEphemeral(path, new Integer(1));
        zkClient.writeData(path, new Integer(1));
    }
}