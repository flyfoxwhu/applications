package com.applications.common.utils;

import org.apache.velocity.*;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by weishuai on 15/11/24.
 */
public class VelocityUtil {

    /**
     * velocity模板合并
     *
     * @param template 模板字符串 如 hello,${name}
     * @param paramMap 参数
     * @return
     * @throws Exception
     */
    public static String merge(String template, Map<String, Object> paramMap) throws Exception {
        VelocityContext vc = new VelocityContext(paramMap);
        StringWriter writer = new StringWriter();
        Velocity.evaluate(vc, writer, "pdf", template);
        return writer.getBuffer().toString();
    }

}
