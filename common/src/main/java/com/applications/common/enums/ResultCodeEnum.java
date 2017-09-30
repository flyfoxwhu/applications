package com.applications.common.enums;

/**
 * Created by hukaisheng on 16/1/14.
 */
public enum ResultCodeEnum {

    FILE_NAME_EMPTY_ERR("3", "存在附件时附件名不能为空"),
    TITLE_SUB_EMPTY_ERR("3", "邮件主题和标题不能同时为空"),
    ;
    private String code;
    private String desc;

    ResultCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
