package com.applications.service.lock;

/**
 * Created by hukaisheng on 16/8/28.
 */
public class ChildSynchronize extends FatherSynchronize implements Runnable {

    final static ChildSynchronize child = new ChildSynchronize();//为了保证锁唯一
    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            new Thread(child).start();
        }
    }

    public synchronized void doSomething() {
        System.out.println("1child.doSomething()");
        doAnotherThing(); // 调用自己类中其他的synchronized方法
    }

    private synchronized void doAnotherThing() {
        super.doSomething(); // 调用父类的synchronized方法
        System.out.println("3child.doAnotherThing()");
    }

    @Override
    public void run() {
        child.doSomething();
    }
}
