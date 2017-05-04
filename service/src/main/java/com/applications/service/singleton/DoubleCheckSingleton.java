package com.applications.service.singleton;

/**
 * @author hukaisheng
 * @date 2017/5/4.
 * 双重check的懒汉模式的单例模式
 * 之所以用volatile，如果有T1，T2两个线程，T1线程运行第六行发现singleton==null，开始对singleton进行实例化，
 * 因为实例化中分为三步，第一步为对象开辟内存空间，第二步为对象初始化，第三步是把这个内存地址赋给singleton，
 * 但是因为java的内存模式允许无序写入，这样一来会导致第二步和第三步位置调换，那么这样一来就坏了，
 * 如果先允许第一步和第三步了，但是此时并没有对对象进行初始化，
 * 恰恰在此时T2经过判断singleton不为null，那么就会返回一个没有被初始化的对象
 */
public class DoubleCheckSingleton {

    private volatile static DoubleCheckSingleton singleton;

    public static DoubleCheckSingleton getSingleton() {
        if (singleton == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}