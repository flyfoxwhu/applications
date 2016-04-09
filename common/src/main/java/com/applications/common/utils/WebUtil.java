package com.applications.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class WebUtil {

    /**
     * 输出到页面
     * <p/>
     * ServletOutputStream servletOutputStream = response.getOutputStream();
     * servletOutputStream.print(str);
     * <p/>
     * ServletOutputStream输出的为二进制流，中文乱码
     *
     * @param resp
     * @param msg
     */
    public static void writerJson(HttpServletResponse resp, String msg) {
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter out = null;
        try {
            out = resp.getWriter();
            out.println(StringUtils.stripToEmpty(msg));
        } catch (IOException e) {
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 是否是ajax请求
     *
     * @param req
     * @return
     */
    public static boolean isJson(HttpServletRequest req) {
        return req.getRequestURI().endsWith(".json");
    }

    /**
     * 获取 redirectURL
     *
     * @param httpRequest
     * @param path
     * @return
     */
    public static String getRedirectUrl(HttpServletRequest httpRequest, String path) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(httpRequest).path(path).queryParam("redirectURL", ServletUriComponentsBuilder.fromRequest(httpRequest).build().encode().toUriString());
        return builder.build().encode().toUriString();
    }

    /**
     * 获取 redirectURL，跳转URL为 referer
     *
     * @param httpRequest
     * @param path
     * @return
     */
    public static String getRedirectUrlFromReferer(HttpServletRequest httpRequest, String path) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(httpRequest).path(path).queryParam("redirectURL", httpRequest.getHeader("referer"));
        return builder.build().encode().toUriString();
    }

    /**
     * 获取URL
     *
     * @param httpRequest
     * @param path
     * @return
     */
    public static String redirectTo(HttpServletRequest httpRequest, String path) {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(httpRequest).path(path);
        return builder.build().encode().toUriString();
    }
}
