package com.applications.service.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hukaisheng on 16/3/9.
 */
public class ThreadPoolFactory {
    private static volatile ExecutorService thredPool = null;
    private static final int CORE_SIZE = 10;
    private static final int MAX_SIZE = 200;
    private static final int KEEP_ALIVE_TIME = 5;

    public static ExecutorService getThreadPool() {
        if (null != thredPool) {
            return thredPool;
        }
        synchronized (ThreadPoolFactory.class) {
            if (null != thredPool) {
                return thredPool;
            }
            thredPool = new ThreadPoolExecutor(CORE_SIZE, MAX_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());
            return thredPool;
        }
    }
}
