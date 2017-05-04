package com.applications.service.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author hukaisheng
 * @date 2017/4/5.
 */
@Component
public class DistributeHandlerTest {

    @Autowired
    private static DistributedLockHandler distributedLockHandler;

    public static void main(String[] args) {
        Lock lock = new Lock("lockk", "sssssssss");
        if (distributedLockHandler.tryLock(lock)) {
            System.out.print("test 1234456");
            distributedLockHandler.releaseLock(lock);
        }
    }

}
