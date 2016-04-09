package com.applications.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

public class PriceUtil {

    /**
     * 12345 -- >123.45
     *
     * @param cent
     * @return
     */
    public static String formatCNY(Long cent) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        return format.format(cent / 100.00);
    }

    public static String formatCNYR(Long cent) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        double price = 0.0;
        try {
            price = cent / 100.00;
        } catch (Exception e) {
        }
        return  Long.toString(Math.round(price));
    }

    public static String formatCNY(Object cent) {
        if (cent == null) {
            return null;
        }
        Long c = Long.valueOf(String.valueOf(cent));
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        return format.format(c / 100.00);
    }

    public static String format(Long cent) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        return format.format(cent / 100.00);
    }

    /**
     * 将分变成元
     * 12345678L  -> 123456.78
     * @param cent
     * @return
     */
    public static String formatNoGroup(Long cent) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setGroupingUsed(false);
        return format.format(cent / 100.00);
    }

    public static String formatWan(Long cent) {
        if (cent == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(cent).divide(new BigDecimal(1000000));
        return bigDecimal.toString();
    }

    /**
     * 1234567800L  -> 123,456.7
     * 123000000L  -> 12,300
     *
     * @param cent
     * @return
     */
    public static String formatCNYW(Long cent) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.DOWN);
        format.setGroupingUsed(true);
        return format.format(cent / 1000000.00);
    }

    /**
     * 元转万
     * @param yuan
     * @return
     */
    public static String formatYToW(Long yuan) {
        if (yuan == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.DOWN);
        format.setGroupingUsed(true);
        return format.format(yuan / 10000.00);
    }

    /**
     * @param price
     * @return
     */
    public static Long convertPrice(String price) {
        if (StringUtils.isBlank(price)){
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(price);
        return bigDecimal.multiply(new BigDecimal(1000000)).longValue();
    }

    /**
     * 将分变成元
     * 12345678L  -> 123456.78
     * 元转换进入数据库
     * @param price
     * @return
     */
    public static Long convertPriceYuan(String price) {
        if (StringUtils.isEmpty(price)){
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(price);
        return bigDecimal.multiply(new BigDecimal(100)).longValue();
    }

    public static long parseCentFromW(String w) {
        if (w == null) {
            return 0;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        try {
            return (format.parse(w)).longValue() * 1000000;
        } catch (Exception e) {

        }
        return -1;
    }

    public static long parseCentFromY(String w) {
        if (w == null) {
            return 0;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        try {
            return (format.parse(w)).longValue() * 100;
        } catch (Exception e) {

        }
        return -1;
    }

    public static String formatYuan(Long cent, boolean groupingUsed, int maximumFractionDigits) {
        if (cent == null) {
            return null;
        }
        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setGroupingUsed(groupingUsed);
        format.setMaximumFractionDigits(maximumFractionDigits);
        return format.format(cent / 100.00);
    }

    public static String formatWYuan(Long cent) {
        if (cent == null) {
            return null;
        }

        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setGroupingUsed(false);
        return format.format(cent / 1000000.00);
    }

    public static String formatWYuanWithPreTen(Long cent) {
        if (cent == null) {
            return null;
        }

        DecimalFormat format = new DecimalFormat();
        format.setCurrency(Currency.getInstance(Locale.CHINA));
        format.setGroupingUsed(false);
        return format.format(cent / 1000000.000);
    }


    private static final String UNIT = "万仟佰拾亿仟佰拾万仟佰拾元角分";

    private static final String DIGIT = "零壹贰叁肆伍陆柒捌玖";

    private static final double MAX_VALUE = 9999999999999.99D;

    /**
     * 1200 -->壹拾贰元整
     * @param cent
     * @return
     */
    public static String toRMB(long cent) {
        if (cent < 0 || cent > MAX_VALUE){
            return "数值位数过大!";
        }
        if (cent == 0){
            return "零元整";
        }
        String result = "";
        if (cent < 0) {//负数
            cent = 0 - cent;
            result = "负";
        }
        String strValue = cent + "";
        // i用来控制数(位)
        int i = 0;
        int len = strValue.length();
        // u用来控制单位(三位)
        int u = UNIT.length() - len;
        boolean isZero = false;
        for (; i < len; i++, u++) {
            char ch = strValue.charAt(i);
            if (ch == '0') {
                isZero = true;
                if (UNIT.charAt(u) == '亿' || UNIT.charAt(u) == '万' || UNIT.charAt(u) == '元') {
                    result = result + UNIT.charAt(u);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    result = result + "零";
                    isZero = false;
                }
                result = result + DIGIT.charAt(ch - '0') + UNIT.charAt(u);
            }
        }

        if (!result.endsWith("分")) {
            result = result + "整";
        }
        result = result.replaceAll("亿万", "亿");
        return result;
    }

    /**
     * 格式化保留2位小数的百分比
     * @param decimal
     * @return
     */
    public static String getPercent(Double decimal) {
        try {
            if(decimal==null){
                return "0.00%";
            }
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);
            return nt.format(decimal);
        } catch (Exception e) {
            return "0.00%";
        }
    }

    public static String format2(double decimal){
        return new Formatter().format("%.2f", decimal).toString();
    }


    public static void main(String[] args) {
        double result=0.7765000000000001;
        BigDecimal bigdec = new BigDecimal(result).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(bigdec + "%");


        System.out.print(PriceUtil.getPercent(result));
    }


}
