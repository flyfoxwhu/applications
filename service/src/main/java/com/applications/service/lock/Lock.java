package com.applications.service.lock;

import lombok.Data;

/**
 * @author hukaisheng
 * @date 2017/4/5.
 */
@Data
public class Lock {
    private String name;
    private String value;

    public Lock(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
