package com.applications.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebContentUtil {
    private static final String h5Anchor="mhc_subscribe_wap";


    /**
     * 读取一个网页全部内容
     */
    public  static String getOneHtml(String htmlurl) {
        URL url;
        String temp;
        final StringBuffer sb = new StringBuffer();
        try {
            //https的开头过滤成http
            if(StringUtils.isBlank(htmlurl)){
                return "";
            }
            if(!htmlurl.contains("http")){
                htmlurl="http://"+htmlurl;
            }
            String finalHtmlUrl=htmlurl.replace("https","http");
            if(!finalHtmlUrl.startsWith("http://www.maihaoche.com")){
                finalHtmlUrl=finalHtmlUrl.replaceFirst("maihaoche.com","www.maihaoche.com");
            }
            url = new URL(finalHtmlUrl);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 读取网页全部内容
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (Exception e) {
            Exception me=e;
            System.out.println("你输入的URL格式有问题！请仔细输入"+me);

        }
        return sb.toString();
    }
    public static  String getTitle(final String url) {
        try {
            return getTitleByPatten(getOneHtml(url));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 通过正则表达式
     * @param s
     * @return 获得网页标题
     */
    public static String getTitleByPatten(final String s)
    {
        String regex;
        String title = "";
        final List<String> list = new ArrayList<String>();
        regex = "<title>.*?</title>";
        final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
        final Matcher ma = pa.matcher(s);
        while (ma.find())
        {
            list.add(ma.group());
        }
        for (int i = 0; i < list.size(); i++)
        {
            title = title + list.get(i);
        }
        return outTag(title);
    }

    /**
     * 通过正则表达式
     * @param url
     * @return 获得网页标题
     */
    public static boolean isH5Page(final String url){
        try {
            String s=getOneHtml(url);
            if(StringUtils.isBlank(s)){
                return false;
            }
            if(s.contains(h5Anchor)){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * @param s
     * @return 获得链接
     */
    public List<String> getLink(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param s
     * @return 获得脚本代码
     */
    public List<String> getScript(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<script.*?</script>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param s
     * @return 获得CSS
     */
    public List<String> getCSS(final String s) {
        String regex;
        final List<String> list = new ArrayList<String>();
        regex = "<style.*?</style>";
        final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
        final Matcher ma = pa.matcher(s);
        while (ma.find()) {
            list.add(ma.group());
        }
        return list;
    }

    /**
     * @param s
     * @return 去掉标记
     */
    public static String outTag(final String s) {
        return s.replaceAll("<.*?>", "");
    }

    /**
     * @param s
     * @return 文章标题及内容
     */
    public HashMap<String, String> getFromYahoo(final String s) {
        final HashMap<String, String> hm = new HashMap<String, String>();
        final StringBuffer sb = new StringBuffer();
        String html = "";
        try {
            html = getOneHtml(s);
        } catch (final Exception e) {
            e.getMessage();
        }

        String title = outTag(getTitleByPatten(s));
        // Pattern pa=Pattern.compile("<div
        // class=\"original\">(.*?)((\r\n)*)(.*?)((\r\n)*)(.*?)</div>",Pattern.DOTALL);
        final Pattern pa = Pattern.compile("<div class=\"original\">(.*?)</p></div>", Pattern.DOTALL);
        final Matcher ma = pa.matcher(html);
        while (ma.find()) {
            sb.append(ma.group());
        }
        String temp = sb.toString();
        temp = temp.replaceAll("(<br>)+?", "\n");// 转化换行
        temp = temp.replaceAll("<p><em>.*?</em></p>", "");// 去图片注释
        hm.put("title", title);
        hm.put("original", outTag(temp));
        return hm;

    }

    /**
     * @param args 测试一组网页，针对雅虎知识堂
     */
    public static void main(final String args[]) {
        String url = "http://maihaoche.com/activities/easyAct.htm?&easy_act_id=59";
        System.out.print(WebContentUtil.getTitle(url));


    }
}
