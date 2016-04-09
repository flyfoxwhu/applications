package com.applications.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils {

    /**
     * 比较两个字符串（大小写不敏感）。
     * <p/>
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "abc")  = false
     * StringUtil.equalsIgnoreCase("abc", null)  = false
     * StringUtil.equalsIgnoreCase("abc", "abc") = true
     * StringUtil.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 要比较的字符串1
     * @param str2 要比较的字符串2
     * @return 如果两个字符串相同，或者都是<code>null</code>，则返回<code>true</code>
     */
    public static boolean isEqualsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }

    public static String[] split(String str, String sep) {
        if (StringUtil.isBlank(str)) {
            return null;
        }
        if (StringUtil.isBlank(sep)) {
            return new String[]{str};
        }
        String[] split = str.split(sep);
        return split;
    }

    public static String array(String str[], Integer index) {
        if (str == null || index == null) {
            return null;
        }
        if (index > str.length - 1 || index < 0) {
            return null;
        }
        return str[index];
    }

    //公司名称
    public static String tansferCompany(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (str.length() > 5) {
            return str.substring(0, 4) + "****";
        }
        return str;
    }

    //隐私名称截取首尾
    public static String transferNick(String str,int size) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (str.length() > size) {
            return str.substring(0, 1) + "**" + str.substring(str.length() - 1, str.length());
        }
        return str;
    }

    //取手机号隐藏中间4位
    public static String transferMobile(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (8 < str.length()) {
            return str.substring(0, 3) + "****" + str.substring(7);
        }
        return str;
    }

    //隐藏身份证号中间若干位
    public static String transferIdentity(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }

        int len = str.length();

        return str.substring(0, 3) + "*************" +  str.substring(len-2);
    }

    public static String cut(String str, Integer len) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (len == null) {
            return str;
        }
        if (len < str.length()) {
            return str.substring(0, len) + "...";
        }
        return str;
    }

    public static String cut(String str, Integer len, String suffix) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        if (len == null) {
            return str;
        }
        if (len < str.length()) {
            return str.substring(0, len) + suffix;
        }
        return str;
    }

    public static List<String> splitStringByLength(String str, Integer len) {
        if (StringUtil.isBlank(str)) {
            return Lists.newArrayList(str);
        }
        if (len == null) {
            return Lists.newArrayList(str);
        }
        List<String> list = new ArrayList<String>();
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            s += c;
            if ((i + 1) % len == 0) {
                list.add(s);
                s = "";
            }
        }
        if(!StringUtil.isBlank(s)){
            list.add(s);
        }
        return list;
    }



    //过滤字符串中的回车换行
    public String clear(String str) {
        if (null == str) {
            return str;
        }

        StringBuilder result = new StringBuilder();

        int length = str.length();
        for (int i = 0; i < length; i++) {
            char tmp = str.charAt(i);
            if (tmp == '\n' || tmp == '\r') continue;
            result.append(tmp);
        }
        return result.toString();
    }

    /**
     * 按照字节切割字符串
     *
     * @param str
     * @param start
     * @param length
     * @return 切割后的字符串
     */
    public static final String substring(String str, int start, int length) {
        if (null == str) {
            return str;
        }

        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        int j = 0;

        for (int i = 0; i < c.length; ++i) {
            char ch = c[i];

            if (ch > 127) {
                j += 2;
            } else {
                j += 1;
            }

            if ((j >= start) && (j < (start + length))) {
                sb.append(ch);
            }

            if (j > (start + length)) {
                break;
            }
        }

        return sb.toString();
    }

    //将整数分数转化为小数
    public static final String showScore(int score) {
        return score / 10 + "." + score % 10;
    }

    public static final String richText(String s) {
        return s;
    }

    //计算评分星星的宽度
    public static final String showScoreWidth(int score) {
        return (score / 10 * 14) + (score % 10 * 14) / 10 - 2 + "." + (score % 10 * 14) % 10;
    }

    /**
     * 字符串长度是否大于limit，汉字长度算2，其他1 大于返回false，小于等于返回true
     *
     * @param t
     * @param limit
     * @return
     */
    public static String showContent(String t, int limit) {
        if (StringUtil.isBlank(t) || checkStringLength(t, limit) || limit <= 3) {
            return t;
        }

        String dot = "...";
        return substring(t, 0, limit) + dot;
    }

    /**
     * 字符串长度是否大于limit，汉字长度算2，其他1 大于返回false，小于等于返回true
     *
     * @param t
     * @param limit
     * @return
     */
    public static boolean checkStringLength(String t, int limit) {
        if (StringUtil.isBlank(t)) {
            return true;
        }

        return (t.getBytes().length - t.length()) / 2 + t.length() <= limit;
    }


    /**
     * URL编码转换
     * @param url
     * @return
     */
    public static String encodeUrl(String url) {
        if (!StringUtil.isBlank(url)) {
            try {
                url = URLEncoder.encode(url, "UTF-8");
                return url;
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
        return "";
    }

    /*
       * 返回32位大写的MD5码
       */
    public static String getEncoderByMd5(String sessionid) {

        StringBuffer hexString = null;
        byte[] defaultBytes = sessionid.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(defaultBytes);
            byte messageDigest[] = algorithm.digest();

            hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                if (Integer.toHexString(0xFF & messageDigest[i]).length() == 1) {
                    hexString.append(0);
                }
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            messageDigest.toString();

        } catch (NoSuchAlgorithmException nsae) {

        }
        return hexString.toString().toUpperCase();

    }

    public static boolean isEmail(String email) {
        String regexp = "^\\S+@[^\\.]\\S*$";

        if (regexp != null && regexp.startsWith("!")) {
            regexp = trimToNull(regexp.substring(1));
        }

        Pattern pattern = Pattern.compile(regexp);
        if (pattern.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    /**
     * 从字符串尾部开始查找指定字符串，并返回第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>。
     * <pre>
     * StringUtil.lastIndexOf(null, *)         = -1
     * StringUtil.lastIndexOf("", *)           = -1
     * StringUtil.lastIndexOf("aabaabaa", 'a') = 7
     * StringUtil.lastIndexOf("aabaabaa", 'b') = 5
     * </pre>
     *
     * @param str       要扫描的字符串
     * @param searchStr 要查找的字符串
     * @return 第一个匹配的索引值。如果字符串为<code>null</code>或未找到，则返回<code>-1</code>
     */
    public static int lastIndexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.lastIndexOf(searchStr);
    }

    public static String getFileName(String downloadUrl) {
        int begin = downloadUrl.lastIndexOf("/");
        int end = downloadUrl.lastIndexOf(".");
        String downloadName = downloadUrl.substring(begin + 1, end);
        try {
            return URLDecoder.decode(downloadName, "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFileType(String downloadUrl) {
        int end = downloadUrl.lastIndexOf(".");
        String downloadType = downloadUrl.substring(end + 1);
        return downloadType;
    }

    public static String html2Text(String inputString) {
        String htmlStr = inputString; // 含html标签的字符串    
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;

        Pattern p_html1;
        Matcher m_html1;
        try {
            String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
            String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
            String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
            String regEx_html1 = "<[^>]+";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 过滤script标签

            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 过滤style标签

            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 过滤html标签

            p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
            m_html1 = p_html1.matcher(htmlStr);
            htmlStr = m_html1.replaceAll(""); // 过滤html标签

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        return textStr;// 返回文本字符串
    }

    public static final char UNDERLINE = '_';

    /**
     * camelToUnderline
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * underlineToCamel
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String replace(String str,String oldStr,String newStr) {
        if (str == null) {
            return null;
        }
        if(oldStr == null || newStr == null){
            return str;
        }
        return str.replaceAll(oldStr, newStr);
    }

    public static void main(String[] args) {
        String a = "<p>\n" +
                "\t微软终于把他家的&nbsp;<a href=\"http://blogs.off";
        System.out.print(html2Text(a));

    }

    public static String getRandom(String radomTitles){
        String[] arr = StringUtil.split(radomTitles, "\\\r\n");
        if( arr!=null && arr.length>0 ){
            int len = arr.length;
            return arr[(new Random()).nextInt(len)];
        }
        return "";
    }

    /**
     * 去除括号中包含的括号和第二层括号里面的内容
     * @param str
     * @return
     */
    public static String getStrRepBracket(String str){
        int before = 0;
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '(') {
                before++;
            }
            if (before <= 1) {
                s += c;
            }
            if (c == ')') {
                before--;
            }
        }
        return s;
    }

}
