package com.applications.service.common.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by hukaisheng on 16/3/17.
 */
@Data
public class CacheForm {

    @NotBlank(message = "会话id不能为空")
    private String sessionId;

    @NotBlank(message = "缓存的key不能为空")
    private String key;
}
