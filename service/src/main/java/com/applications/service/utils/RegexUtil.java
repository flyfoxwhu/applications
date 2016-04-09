package com.applications.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class RegexUtil {
    /**
     * 手机
     */
    public static final String PHONE_REGEX = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[03678])|(18[0,0-9]))\\d{8}$";

    /**
     * 邮箱
     */
    public static final String EMAILREGEX = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$";

    /**
     * textarea 获取图片名称
     */
    public static final String TEXTAREAIMGREGEX = "imageName=[0-9]+.[a-zA-Z]+";

    /**
     * 仅匹配获取img标签，和属性无关
     */
    public static final String IMGTAGREG = "<img[^>]+src=[\"'][^\"']+\"[^>]*>";

    /**
     * html字符串中，匹配img标签以及img标签中src的属性值【当alt包含<>特殊符号，文件名包含空格时依然适用】
     * <p/>
     * 示例：<img alt=\"图片1 <<1>>.png\" src=\"http://xx_x_ _x.jpg\"/>
     * <p/>
     * 匹配后可获取整个img标签和【http://xx_x_ _x.jpg】
     */
    public static final String IMG_REGEX = "<img.*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

    /**
     * html标签
     */
    public static final String HTMLTAGREG = "<[\\w]+?\\s*[^><]*\\s*>|<\\s*\\/[\\w]+\\s*>";

    /**
     * 邮编
     */
    public static final String POSTCODE = "^[1-9][0-9]{5}$";

    /**
     * 引号
     */
    public static final String QOUTREGX = "(\"|&quot;|&quot)";

    /**
     * 用户名：字母、数字、下划线，以字母开头，6到18位
     */
    public static final String LOGIN_NAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 密码：字母、数字、下划线，6到18位
     */
    public static final String PASSWORD = "^\\w{5,17}$";

    /**
     * 用户登录名：只能输入中文、英文、数字：不以数字开头，长度6~18
     */
    public static final String NEW_LOGIN_NAME = "^[a-zA-Z]{1}[a-zA-Z0-9]{5,17}";

    /**
     * 用户登录名：只能输入中文、英文、数字：不以数字开头，长度6~18
     */
    public static final String OLD_LOGIN_NAME = "^[a-zA-Z\u4e00-\u9fa5]{1}[\u4e00-\u9fa5a-zA-Z0-9]{5,17}";

    /**
     * 数字
     */
    public static final String NUMBER = "^[0-9]*$";

    /**
     * 字母
     */
    public static final String LETTER = "^[A-Za-z]+$";

    /**
     * 大写字母
     */
    public static final String CAPITAL_LETTER = "^[A-Z]+$";

    /**
     * 小写字母
     */
    public static final String LOWERCASE_LETTERS = "^[a-z]+$";

    /**
     * 英文和数字
     */
    public static final String NUMBER_LETTERS = "^[A-Za-z0-9]+$";

    /**
     * 身份证位数校验
     */
    public static final String IDENTY = "^\\d{15}$|^\\d{18}$";

    /**
     * 校验value是否匹配regex所指定的格式
     *
     * @param value
     * @param regex
     * @return
     */
    public static Boolean match(String value, String regex) {
        return Pattern.matches(regex, value);
    }

    /**
     * 格式化关键词模糊查询str，将空格、中英文逗号统一转换为value
     *
     * @param keyWord
     * @param value
     * @return
     */
    public static String formatKeyWord(String keyWord, String value) {
        if (null == keyWord) {
            return null;
        }
        return keyWord.replaceAll("，+", value).replaceAll("\\s+", value).replaceAll("\"+", "\"").replaceAll(",+", value);
    }

    /**
     * 解析文本中嵌入的图片链接，嵌入的超链接中文件的参数名为imageName， 例如 <img
     * src="image/get.html?imageName=12323243.png&ownerId=1"/>
     * 解析后得到的结果为：12323243.png
     *
     * @param content
     * @return 默认返回值为空字符串
     */
    public static String findImagNameOfText(String content) {
        if (content == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Matcher ma = Pattern.compile(TEXTAREAIMGREGEX).matcher(content);
        while (ma.find()) {
            sb.append(ma.group().split("=")[1]).append(",");
        }
        if (sb.length() > 0) {
            return sb.substring(0, sb.length() - 1);
        }
        return "";

    }

    public static List<String> getImagNameOfText(String content) {
        List<String> imagesList = new ArrayList<String>(0);

        if (isNotBlank(content)) {
            imagesList = asList(findImagNameOfText(content).split(","));
        }

        return imagesList;
    }

    /**
     * 删除文本中的html和图片标签，只显示标签中的文字
     *
     * @param content
     * @return
     */
    public static String removeImagAndHTMLTag(String content) {
        if (content == null) {
            return null;
        }
        return content.replaceAll(IMGTAGREG, "").replaceAll(HTMLTAGREG, "");

    }

    public static String repaceQout(String content) {
        if (content == null) {
            return null;
        }
        return content.replaceAll(QOUTREGX, "\\\"");

    }

    public static void main(String[] args) {
        System.out.println(match("11 11", "\\S+"));
        System.out.println(match(" 1111", "\\S+"));
        System.out.println(match("1111 ", "\\S+"));
        System.out.println(match("1111", "\\S+"));
    }
}
