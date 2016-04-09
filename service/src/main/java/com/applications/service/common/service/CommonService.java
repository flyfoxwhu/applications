package com.applications.service.common.service;

import com.applications.service.common.dto.EmailForm;
import com.applications.service.utils.BizResult;

/**
 * Created by hukaisheng on 16/4/9.
 */
public interface CommonService {

    /**
     * 发送邮件，可以发送附件，当附件地址不为空时需要附件的名称
     * @param form
     * @return
     */
    BizResult sendEmail(EmailForm form);
}
