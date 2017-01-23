package com.applications.service.masterWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by hukaisheng on 2016/12/6.
 * Master需要接受Client端提交过来的任务Task，而且还得将Task分配给Worker进行处理，
 * 因此Master需要一个存储来存放Task。那么采用哪种存储集合呢？
 * 首先来说，需要支持并发的集合类，因为多个Worker间可能存在任务竞争，
 * 因此我们需要考虑java.util.concurrent包下的集合。这里可以考虑采用非阻塞的ConcurrentLinkedQueue。
 * Master需要清楚的知道各个Woker的基本信息，如是否各个Worker都运行完毕，
 * 因此Master端需要保存Worker的信息，可以采用Map存储。
 * 由于最后各个Worker都会上报运行结果，Master端需要有一个存储结果的Map，可以采用支持并发的ConcurrentHashMap。
 */
public class Master {

    private ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<>();

    private Map<Long,Thread> workers = new HashMap<>();

    private ConcurrentHashMap<Long,Object> results = new ConcurrentHashMap<>();

    //通过构造方法以初始化workers
    public Master(int num){

        for(int i = 0 ; i < num ; i++){

            Worker worker = new Worker(i,"worker-" + i);
            worker.setResults(results);
            worker.setWorkQueue(workQueue);

            workers.put(Long.valueOf(i),new Thread(worker));
        }

    }

    //提供submit(Task)方法接受Client端提交过来的任务
    public void submit(Task task){
        workQueue.add(task);
    }

    //让workers开始处理任务
    public void start(){

        for (Map.Entry<Long,Thread> entry : workers.entrySet()){

            entry.getValue().start();
        }

    }

    //判断各个worker的状态，是否都处理完毕
    public boolean isComlepte(){

        for(Map.Entry<Long,Thread> entry : workers.entrySet()){

            if(entry.getValue().getState() != Thread.State.TERMINATED){
                return false;
            }

        }

        return true;
    }

    //给客户端返回结果
    public long getSumResult(){

        long value = 0;
        for(Map.Entry<Long,Object> entry : results.entrySet()){

            value = value + (Long)entry.getValue();

        }
        return value;
    }
}
