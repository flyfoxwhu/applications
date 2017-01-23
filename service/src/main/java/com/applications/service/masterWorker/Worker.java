package com.applications.service.masterWorker;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by hukaisheng on 2016/12/6.
 * Worker本质上就是Runnable，提供run()
 * 负责处理业务逻辑的handle()
 * Worker需要持有Master端的任务Task集合的引用，因为Worker需要从里面拿取Task。
 * 同上，Worker需要持有Master端的存储结果的引用
 */
public class Worker implements Runnable {
    private long id;
    private String name;

    private ConcurrentLinkedQueue<Task> workQueue;

    private ConcurrentHashMap<Long,Object> results;

    public void setWorkQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResults(ConcurrentHashMap<Long, Object> results) {
        this.results = results;
    }

    public Worker(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void run() {

        while(true){

            Task task = workQueue.poll();

            if(task == null){
                break;
            }

            long start = System.currentTimeMillis();
            long result = handle(task);

            this.results.put(task.getId(),result);

            System.out.println(this.name + " handle " + task.getName() + " success . result is " + result + " cost time : " + (System.currentTimeMillis() - start));
        }
    }

    /**
     * 负责处理具体业务逻辑
     * @param task
     * @return
     */
    private long handle(Task task) {

        //这里只是模拟下，在真实环境根据实际业务来定
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new Random().nextLong();
    }
}
