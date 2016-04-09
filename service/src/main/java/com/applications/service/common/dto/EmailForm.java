package com.applications.service.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hukaisheng on 16/3/8.
 */
@Data
public class EmailForm {

    @NotBlank(message = "邮箱地址不能为空")
    private String emailAddress;

    //附件地址不为空时附件名不能为空
    private String fileName;

    //附件地址
    private String fileUrl;

    /**
     * title和subtitle不能同时为空
     */
    private String title;//用于生成邮件主题

    private String content;//邮件内容

    private String subtitle;//邮件主题
}
