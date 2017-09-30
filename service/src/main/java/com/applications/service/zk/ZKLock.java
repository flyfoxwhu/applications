package com.applications.service.zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hukaisheng
 * @date 2017/9/30.
 */
public class ZKLock {

    //提前创建好锁对象的结点"/lock" CreateMode.PERSISTENT
    public static final String LOCK_ZNODE = "/lock";
    //分布式锁实现分布式seq生成
    public static class ZKLockTask implements Runnable, IZkChildListener {

        private final String taskName;

        private final ZkClient zkClient;

        private final String lockPrefix = "/loc";

        private final String selfZnode;

        public ZKLockTask(String taskName) {
            this.taskName = taskName;
            zkClient = new ZkClient("172.21.10.42:2181", 30000, 50000);
            selfZnode = zkClient.createEphemeralSequential(LOCK_ZNODE + lockPrefix, new byte[0]);
        }

        @Override
        public void run() {

            createSeq();
        }

        private void createSeq() {
            Stat stat = new Stat();
            byte[] oldData = zkClient.readData(LOCK_ZNODE, stat);
            byte[] newData = update(oldData);
            zkClient.writeData(LOCK_ZNODE, newData);
            System.out.println(taskName + selfZnode + " obtain seq=" + new String(newData));
        }

        private byte[] update(byte[] currentData) {
            String s = new String(currentData);
            int d = Integer.parseInt(s);
            d = d + 1;
            s = String.valueOf(d);
            return s.getBytes();
        }

        @Override
        public void handleChildChange(String parentPath,
                                      List<String> currentChildren) throws Exception {

        }

    }


    public static void main(String[] args) {

        final ExecutorService service = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            service.execute(new ZKLockTask("[Concurrent-" + i + "]"));
        }
        service.shutdown();
    }
}
