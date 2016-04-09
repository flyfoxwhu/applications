package com.applications.common.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class JsonFormValidator {
    public static JsonResult validate(Errors errors) {
        JsonResult jsonResult = JsonResult.getInstance();
        if (errors.hasErrors()) {
            StringBuffer sb = new StringBuffer();
            if (!CollectionUtils.isEmpty(errors.getAllErrors())) {
                for (ObjectError error : errors.getAllErrors()) {
                    sb.append(error.getDefaultMessage() + ";");
                }
            }
            jsonResult.setErrCode(JsonResult.FAILED_CODE, sb.toString());
        }
        return jsonResult;
    }
}
