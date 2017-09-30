package com.applications.service.zk;

import com.github.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hukaisheng
 * @date 2017/9/30.
 */
public class ZKSeqTest {

    //提前创建好存储Seq的"/createSeq"结点 CreateMode.PERSISTENT
    public static final String SEQ_ZNODE = "/seq";

    //通过znode数据版本实现分布式seq生成
    public static class Task1 implements Runnable {
        private final String taskName;
        public Task1(String taskName) {
            this.taskName = taskName;
        }
        @Override
        public void run() {
            ZkClient zkClient = new ZkClient("172.21.10.42:2181", 3000, 50000);
            Stat stat =zkClient.writeData(SEQ_ZNODE, new byte[0], -1);
            int versionAsSeq = stat.getVersion();
            System.out.println(taskName + " obtain seq=" +versionAsSeq );
            zkClient.close();
        }
    }

    public static void main(String[] args) {
        final ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.execute(new Task1("[Concurrent-" + i + "]"));
        }
    }
}
