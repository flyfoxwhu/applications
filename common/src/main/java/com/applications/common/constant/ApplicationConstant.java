package com.applications.common.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 普通常量
 */
public abstract class ApplicationConstant {

    public static final String SPLIT = ";";

    public static final String UTF = "utf-8";

    public static final int EMAIL_TIMEOUT= 60*3;

    private static final Map<Integer, String> MESSAGES = new HashMap<>();

    static {
        MESSAGES.put(ResultCode.SUCCESS, "成功");
        MESSAGES.put(ResultCode.NO_DATA, "无数据");
        MESSAGES.put(ResultCode.PARAM_ERROR, "参数错误");
        MESSAGES.put(ResultCode.INSERT_FAIL, "插入数据错误");
        MESSAGES.put(ResultCode.QUERY_FAIL, "查询错误");
        MESSAGES.put(ResultCode.UPDATE_FAIL, "修改数据错误");
        MESSAGES.put(ResultCode.EXCEPTION, "系统异常");
        MESSAGES.put(ResultCode.WRONG_PASSWORD, "密码错误");
        MESSAGES.put(ResultCode.WRONG_ACCOUNT_OR_PASSWORD, "密码错误或非供应商账号");
        MESSAGES.put(ResultCode.INVALID_SECURE_CODE, "验证码错误");
        MESSAGES.put(ResultCode.NOT_SUPPLIER_ACCOUNT, "非供应商账号");
        MESSAGES.put(ResultCode.SECURE_CODE_VERIFIED, "尚未通过图形验证");
    }

    public static String getMessage(int code) {
        return MESSAGES.get(code);
    }

    public final class ResultCode {
        public static final int SUCCESS = 1001;
        public static final int NO_DATA = 1002;
        public static final int PARAM_ERROR = 1003;
        public static final int INSERT_FAIL = 1004;
        public static final int QUERY_FAIL = 1005;
        public static final int UPDATE_FAIL = 1006;
        public static final int EXCEPTION = 1007;
        public static final int WRONG_PASSWORD = 1008;
        public static final int INVALID_SECURE_CODE = 1009;
        public static final int WRONG_ACCOUNT_OR_PASSWORD = 1010;
        public static final int NOT_SUPPLIER_ACCOUNT = 1011;
        public static final int SECURE_CODE_VERIFIED = 1012;
    }


    //验证邮件模板
    public static final String CAR_EDU_MAIL_CONTENT = "<div>\n" +
            "<p style=\"text-align:left; padding-bottom: 30px; margin:0;\"></p>\n" +
            "<p style=\"padding-bottom: 5px; margin:0 5px 0 0;\">您好：</p>\n" +
            "<p style=\"text-indent: 20px; padding:0; margin:0\">XXXXXXXXXXXX，请查收！</p>\n" +
            "<p style=\"padding:0; margin:0\">谢谢!</p>\n" +
            "<p style=\"padding:0; margin:0\">XXXXXXX</p>\n" +
            "</div>";

    //验证邮件标题
    public static final String CAR_EDU_TITLE = "XXXXXX—%s";


    public static final String OK = "ok";

    public static final String NO = "no";

    public static final String REPEAT_CODE = "REPETITION_ERROR";
}
