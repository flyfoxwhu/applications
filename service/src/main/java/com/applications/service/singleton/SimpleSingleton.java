package com.applications.service.singleton;

/**
 * @author hukaisheng
 * @date 2017/5/4.
 * 不能保证线程安全的懒汉模式的单例模式
 */
public class SimpleSingleton {

    private static SimpleSingleton singleton;

    public static SimpleSingleton getSingleton() {
        if (singleton == null) {
            singleton = new SimpleSingleton();
        }
        return singleton;
    }
}
