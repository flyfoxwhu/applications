package com.applications.service.common.dto;

import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by zhangwei on 16/1/18.
 */
@ToString
public class SendSecurityCodeForm {
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @NotBlank(message = "发送类型不能为空")
    private String type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
