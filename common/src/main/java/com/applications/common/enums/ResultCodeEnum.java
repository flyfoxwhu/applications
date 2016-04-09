package com.applications.common.enums;

/**
 * Created by hukaisheng on 16/1/14.
 */
public enum ResultCodeEnum {
    USER_EXIST("4", "用户已经存在"),
    USER_NOT_EXIST("3", "用户不存在"),
    PASSWORD_ERR("3", "密码错误"),
    SYSTEM_ERR("3", "系统错误"),


    MOBILE_NOTEXIST("3", "手机号码不存在"),
    UPDATE_FAILED("3", "更新失败"),
    UPDATE_USERINFO_FAILED("3", "更新个人资料失败"),

    REGISTER_MOBILE_EXISTED("3", "该手机号已经被注册"),
    REGISTER_FAILED("3", "注册失败"),


    GET_UPTOKEN_FAILED("3", "获取uptoken失败"),

    SEND_SECCODE_FAILED("3", "发送验证码失败"),
    VERIFY_SECCODE_FAILED("3", "验证码校验不通过或已超时"),
    INVALID_SECCODE_TYPE("3", "发送枚举值有误"),
    INVALID_MOBILE("3", "手机号码格式有误"),


    DEL_CONTACT_FAILED("3", "删除联系人失败"),

    NOT_OFFER_YET("3", "暂未提供"),

    CONTACT_EXIST("3", "该联系人已经存在"),
    ADD_CONTACT_FAILED("3", "新增联系人失败"),

    INVALID_ID("3", "id不合法"),
    INVALID_SESSION("3", "通过session无法找到用户"),
    ADD_ORDER_FAILED("3", "创建垫资订单失败"),
    DUBBO_SERVICE_ERR("3", "调用接口异常，未返回数据"),
    SENDMAIL_SERVICE_ERR("3", "发送提车单邮件异常"),
    CALL_SELLER_ERR("3", "呼叫用户失败"),
    FILE_NAME_EMPTY_ERR("3", "存在附件时附件名不能为空"),
    TITLE_SUB_EMPTY_ERR("3", "邮件主题和标题不能同时为空"),
    SEND_SMS_FREQ_FAILED("3", "请求过于频繁"),

    DEL_CLIENT_CLUE_FAILED("3", "删除客户线索失败"),
    SERVER_ERR("5", "服务器异常"),
    OPERATE_TIMEOUT("3", "等待时间过长,请重新操作"),
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
