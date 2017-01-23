package com.applications.service.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by hukaisheng on 15/11/17.
 */
public class AsyncTaskUtil implements Runnable {
    protected static final Log log = LogFactory.getLog(AsyncTaskUtil.class);
    Object inner_invoker;

    Method inner_method;

    List<?> inner_paraArray;

    public AsyncTaskUtil(Object invoker, Method method, List<?> list) {
        inner_invoker = invoker;
        inner_method = method;
        inner_paraArray = list;

    }
    @Override
    public void run() {
        try {
            for(Object obj:inner_paraArray){
                inner_method.invoke(inner_invoker, obj);
            }
        } catch (Exception e) {
            log.error("AsynExecutor" + "\t" + inner_method.getClass() + "\t"
                    + inner_method.getName() + "\t" + "error:"+e.getStackTrace());
        }
    }
}
