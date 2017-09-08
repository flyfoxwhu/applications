package com.applications.common.enums;

public enum PrefixEnum {

    mobile("m:"),//手机验证码
    SEND_MAIL_ADDRESS("sendMailAddress:")
    ;

    String value;

    PrefixEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
