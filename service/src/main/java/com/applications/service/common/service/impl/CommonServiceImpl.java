package com.applications.service.common.service.impl;

import com.applications.common.constant.ApplicationConstant;
import com.applications.common.enums.PrefixEnum;
import com.applications.common.utils.DownloadFileUtil;
import com.applications.common.utils.KeyUtil;
import com.applications.common.utils.SendEmailUtil;
import com.applications.service.common.dto.EmailForm;
import com.applications.service.common.service.CommonService;
import com.applications.service.redis.intf.RedisManager;
import com.applications.service.utils.BizResult;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hukaisheng on 16/4/9.
 */
@Service("commomService")
public class CommonServiceImpl implements CommonService {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private RedisManager redisManager;

    @Override
    public BizResult sendEmail(EmailForm form) {
        BizResult bizResult = new BizResult();
        try {
            //缓存中存在表明在3分钟内已经发过该订单对应类型的邮件
            String sendKey = KeyUtil.getKey(form.getEmailAddress(), PrefixEnum.SEND_MAIL_ADDRESS);
            String isSend = redisManager.getString(sendKey);
            if (!StringUtils.isBlank(isSend)){
                bizResult.setSuccess(true);
                return bizResult;
            }
            String subtitle = form.getSubtitle();
            String content = form.getContent();
            if (StringUtils.isBlank(subtitle)){
                subtitle = String.format(ApplicationConstant.CAR_EDU_TITLE, form.getTitle());
            } else {
                subtitle = URLDecoder.decode(subtitle, ApplicationConstant.UTF);
            }

            if (StringUtils.isBlank(content)){
                content = ApplicationConstant.CAR_EDU_MAIL_CONTENT;
            } else {
                content = URLDecoder.decode(content,ApplicationConstant.UTF);
            }

            List<File> files = new ArrayList<>();
            //附件的地址不为空时
            if (!StringUtils.isBlank(form.getFileUrl())) {
                String fileName = form.getFileName();
                fileName = URLDecoder.decode(fileName, ApplicationConstant.UTF);
                String tmpPath = this.getClass().getResource(File.separator).getPath()+File.separator+"tmp";
                File file = DownloadFileUtil.getFile(form.getFileUrl(), fileName, tmpPath);
                files.add(file);
            }

            bizResult.setSuccess(SendEmailUtil.sendWithHtml(form.getEmailAddress(), subtitle, content, files));
            if (bizResult.isSuccess()) {
                redisManager.put(sendKey, ApplicationConstant.OK, ApplicationConstant.EMAIL_TIMEOUT);
            }
        } catch (Exception e) {
            log.error("发送邮件异常", e);
            bizResult.setSuccess(false);
            bizResult.setErrMsg("发送邮件服务异常");
        }
        return bizResult;
    }
}
