package com.applications.common.dto;

import lombok.Data;

/**
 * Created by hukaisheng on 16/3/9.
 */
public class CommonForm {

    /**
     * APP端的平台信息
     */
    private String TTID;

    public String getTTID() {
        return TTID;
    }

    public void setTTID(String TTID) {
        this.TTID = TTID;
    }
}
