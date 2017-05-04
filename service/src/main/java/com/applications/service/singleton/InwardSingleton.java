package com.applications.service.singleton;

/**
 * @author hukaisheng
 * @date 2017/5/4.
 * 通过内部类的方式来实现单例模式，既保证了懒加载，同时也保证了线程安全问题
 */
public class InwardSingleton {
    private static class SingletonManager {
        private final static InwardSingleton SINGLETON = new InwardSingleton();
    }

    public final static InwardSingleton getSingleton() {
        return SingletonManager.SINGLETON;
    }
}