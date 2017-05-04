package com.applications.service.singleton;

/**
 * @author hukaisheng
 * @date 2017/5/4.
 * 饿汉模式的单例模式
 */
public class HungrySingleton {

    private static HungrySingleton singleton = new HungrySingleton();

    public static HungrySingleton getSingleton() {
        return singleton;
    }
}
