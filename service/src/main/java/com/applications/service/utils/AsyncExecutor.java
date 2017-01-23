package com.applications.service.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by hukaisheng on 15/11/17.
 */
public class AsyncExecutor {
    final static Log logger = LogFactory.getLog(AsyncExecutor.class);
    private static Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();
    private static ExecutorService exec = new ThreadPoolExecutor(20, 50, 6000,
            TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000));
    public static  void asynInvoke(Object invoker, String methodName,
                                   List<?> list) throws Exception {
        try {
            Method method = getMethod(invoker, methodName);
            AsyncTaskUtil asyncTask = new AsyncTaskUtil(invoker, method, list);
            // 该线程池使用有界队列，防止内存溢出，并且如果队列满时，新任务提交过来时，会直接抛出RejectedExecutionException溢出
            //不会阻塞当前业务线程
            exec.execute(asyncTask);
        } catch (RejectedExecutionException e) {
            logger.error("queue is full!",e);
        } catch (Exception e) {
            logger.error("asynInvoke error!",e);
        }

    }
    public static  void asynInvokeSingle(Object invoker, String methodName,
                                         Object object) throws Exception {
        try {
            Method method = getMethod(invoker, methodName);
            List<Object> list=new ArrayList<>();
            list.add(object);
            AsyncTaskUtil asyncTask = new AsyncTaskUtil(invoker, method, list);
            // 该线程池使用有界队列，防止内存溢出，并且如果队列满时，新任务提交过来时，会直接抛出RejectedExecutionException溢出
            //不会阻塞当前业务线程
            exec.execute(asyncTask);
        } catch (RejectedExecutionException e) {
            logger.error("queue is full!",e);
        } catch (Exception e) {
            logger.error("asynInvoke error!",e);
        }

    }

    private static Method getMethod(final Object invoker, String methodName)
            throws Exception {
        String methodKey = invoker.getClass().getName() + "." + methodName;
        if (methodMap != null && methodMap.get(methodKey) != null) {
            return methodMap.get(methodKey);
        }

        Method[] methods = invoker.getClass().getDeclaredMethods();
        Method hitMethod = null;
        int hitCount = 0;
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName().equals(methodName)) {
                hitCount++;
                methods[i].setAccessible(true);
                hitMethod = methods[i];
            }
        }
        if (hitCount == 0) {
            throw new NoSuchMethodException();
        } else if (hitCount > 1) {
            throw new Exception("Doesn't support overLoad, try another method");
        }

        if (methodMap != null && hitMethod != null) {
            methodMap.put(methodKey, hitMethod);
        }

        return hitMethod;
    }

    /**
     * 线程任务
     * @param task 实现了Runnable接口的任务类
     */
    public static void executeTask(Runnable task) {
        try {
            exec.execute(task);
        } catch (Exception e) {
            logger.error("线程池执行任务异常", e);
        }
    }
}
