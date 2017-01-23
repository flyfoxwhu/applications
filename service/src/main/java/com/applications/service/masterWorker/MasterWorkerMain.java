package com.applications.service.masterWorker;

/**
 * Created by hukaisheng on 2016/12/6.
 */
public class MasterWorkerMain {

    public static void main(String[] args) {

        Master master = new Master(10);

        for(int i = 0 ; i < 10 ; i++){

            Task task = new Task(i,"task-" + i);

            master.submit(task);
        }

        long start = System.currentTimeMillis();
        master.start();

        while(true){

            if(master.isComlepte()){

                System.out.println("sum result is " + master.getSumResult() + " . cost time : " + (System.currentTimeMillis() - start));
                break;
            }
        }


    }
}
