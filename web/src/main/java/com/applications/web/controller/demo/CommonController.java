package com.applications.web.controller.demo;

import com.applications.common.config.InterfaceLog;
import com.applications.common.enums.ResultCodeEnum;
import com.applications.common.utils.JsonFormValidator;
import com.applications.common.utils.JsonResult;
import com.applications.service.common.dto.EmailForm;
import com.applications.service.common.service.CommonService;
import com.applications.service.utils.BizResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * Created by hukaisheng on 16/4/9.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private CommonService commonService;


    /**
     * 根据文件的链接地址、邮箱地址和文件名发送邮件
     * @param form
     * @param errors
     * @return
     */
    @ResponseBody
    @InterfaceLog(interName = "common/sendEmail.json")
    @RequestMapping(value = "/sendEmail.json", method = {RequestMethod.GET,RequestMethod.POST})
    public Object sendEmail(@RequestBody @Valid EmailForm form, Errors errors) {
        JsonResult jsonResult = JsonFormValidator.validate(errors);
        if (!jsonResult.isSuccess()) {
            return jsonResult.getMap();
        }
        /**
         * 附件地址不为空时附件名不能为空
         */
        if (!StringUtils.isBlank(form.getFileUrl()) && StringUtils.isBlank(form.getFileName())) {
            jsonResult.setErrCode(ResultCodeEnum.FILE_NAME_EMPTY_ERR);
            return jsonResult.getMap();
        }

        //邮件主题和标题不能同时为空
        if (StringUtils.isBlank(form.getSubtitle()) && StringUtils.isBlank(form.getTitle())) {
            jsonResult.setErrCode(ResultCodeEnum.TITLE_SUB_EMPTY_ERR);
            return jsonResult.getMap();
        }

        BizResult bizResult = commonService.sendEmail(form);
        if (!bizResult.isSuccess()){
            jsonResult.setErrMsg(bizResult.getErrMsg());
        }

        return jsonResult.getMap();
    }

}
