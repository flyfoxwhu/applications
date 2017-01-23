package com.applications.service.masterWorker;

import lombok.Data;

/**
 * Created by hukaisheng on 2016/12/6.
 */
@Data
public class Task {

    private long id;
    private String name;

    public Task(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
