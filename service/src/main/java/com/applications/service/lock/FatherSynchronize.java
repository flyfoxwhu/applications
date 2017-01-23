package com.applications.service.lock;

/**
 * Created by hukaisheng on 16/8/28.
 */
public class FatherSynchronize {

    public synchronized void doSomething() {
        System.out.println("2father.doSomething()");
    }
}
