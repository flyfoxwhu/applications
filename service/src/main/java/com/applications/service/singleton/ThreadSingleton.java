package com.applications.service.singleton;

/**
 * @author hukaisheng
 * @date 2017/5/4.
 * 懒汉模式的单例模式，虽然保证了线程安全，但是性能较差
 */
public class ThreadSingleton {

    private static ThreadSingleton singleton;

    public synchronized static ThreadSingleton getSingleton() {
        if (singleton == null) {
            singleton = new ThreadSingleton();
        }
        return singleton;
    }
}