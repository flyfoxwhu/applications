package com.applications.common.utils;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ChangeUtil {

    public static Long getLong(String key, Map m) {
        if (m == null || key == null || m.get(key) == null) {
            return null;
        }
        Object o = m.get(key);
        if (isNumber(o.toString())) {
            return Long.parseLong(o.toString());
        }else {
            return null;
        }
    }

    public static String getString(String key, Map m) {
        if (m == null || key == null || m.get(key) == null) {
            return null;
        }
        return String.valueOf(m.get(key));
    }

    public static Integer getInt(String key, Map m) {
        if (m == null || key == null || m.get(key) == null) {
            return null;
        }
        Object o = m.get(key);
        if (isNumber(o.toString())) {
            return Integer.parseInt(o.toString());
        }else {
            return null;
        }
    }

    public static List<String> getList(String target, String regex) {
        List<String> l = new ArrayList<String>();
        l.add(target);
        return getList(l, regex);
    }

    /**
     * 对list进行分割，通过regex分隔符
     * @param list
     * @param regex
     * @return
     */
    public static List<String> getList(List<String> list, String regex) {
        if (CollectionUtils.isEmpty(list)) return new ArrayList<String>();

        List<String> l = new ArrayList<String>();
        for (String s1 : list) {
            if (StringUtils.isBlank(s1)) {
                continue;
            }
            String[] arr = StringUtils.split(s1, regex);
            if (arr == null) {
                continue;
            }
            for (String s : arr) {
                if (!StringUtils.isBlank(s) && !StringUtils.isBlank(s.trim())) {
                    l.add(s.trim());
                }
            }
        }
        return l;
    }

    /**
     * 对str进行分割，包括多重分割符号
     * @param str
     * @return
     */
    public static List<String> getListByAllSplit(String str) {
        if (StringUtils.isBlank(str)) {
            return Lists.newArrayList();
        }
        char c = 160;
        String[] repList = {String.valueOf(c), "，", ",", "、", "。", ".", "(", ")", "（", "）", "\n", "\t", " ","\u2795"};
        List<String> list = ChangeUtil.getList(str.replace("\"", ""), ",");
        for (String rep : repList) {
            list = ChangeUtil.getList(list, rep);
        }
        return list;
    }

    public static String transferString(Collection<?> l) {
        if (org.springframework.util.CollectionUtils.isEmpty(l)) return "";
        StringBuffer sb = new StringBuffer();
        for (Object o : l) {
            sb.append(o).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
    public static String transferString(Collection<?> l, String regex) {
        if (org.springframework.util.CollectionUtils.isEmpty(l)) return "";
        StringBuffer sb = new StringBuffer();
        for (Object o : l) {
            sb.append(o).append(regex);
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static List<Long> transferList(long[] l) {
        if (l == null) {
            return new ArrayList<Long>();
        }

        List<Long> longs = new ArrayList<Long>();
        for (long o : l) {
            longs.add(o);
        }
        return longs;
    }

    public static List<Integer> transferIntegerList(int[] l) {
        if (l == null) {
            return new ArrayList<Integer>();
        }
        List<Integer> longs = new ArrayList<Integer>();
        for (int o : l) {
            longs.add(o);
        }
        return longs;
    }

    public static List<Long> getIdsList(String objIds) {
        if (!StringUtils.isBlank(objIds)) {
            List<Long> idsList = new ArrayList<Long>();
            String[] arr = StringUtils.split(objIds, ",");
            if (arr != null)
                for (String s : arr) {
                    if (!StringUtils.isBlank(s)) {
                        idsList.add(Long.parseLong(s));
                    }
                }
            return idsList;
        }
        return new ArrayList<Long>();
    }

    public static List<Long> getIdsList(Collection<String> itemIds) {
        if (!CollectionUtils.isEmpty(itemIds)) {
            List<Long> idsList = new ArrayList<Long>();
            for (String sid : itemIds) {
                idsList.add(Long.parseLong(sid));
            }
            return idsList;
        }
        return new ArrayList<Long>();
    }

    public static Set<Long> getIdsSet(String objIds) {
        if (!StringUtils.isBlank(objIds)) {
            Set<Long> idsList = new LinkedHashSet<Long>();
            String[] arr = StringUtils.split(objIds, ",");
            if (arr != null)
                for (String s : arr) {
                    if (!StringUtils.isBlank(s)) {
                        idsList.add(Long.parseLong(s));
                    }
                }
            return idsList;
        }
        return new HashSet<Long>();
    }

    public static List<Integer> getInteger(String objIds) {
        if (!StringUtils.isBlank(objIds)) {
            List<Integer> idsList = new ArrayList<Integer>();
            String[] arr = StringUtils.split(objIds, ",");
            if (arr != null)
                for (String s : arr) {
                    if (!StringUtils.isBlank(s)) {
                        idsList.add(Integer.parseInt(s));
                    }
                }
            return idsList;
        }
        return new ArrayList<Integer>();
    }

    public static List<Long> transfer(Long id) {
        List<Long> itemIds = new ArrayList<Long>();
        itemIds.add(id);
        return itemIds;
    }


    public static String transferStandard(int[] standard, String old) {
        if (standard == null) {
            return old;
        }
        Set<Integer> s = new HashSet<Integer>();
        if (!StringUtils.isBlank(old)) {
            s.addAll(getInteger(old));
        }
        for (int s1 : standard) {
            s.add(s1);
        }
        return transferString(s);
    }

    public static boolean isNumber(String str){
        if(!StringUtils.isBlank(str)){
            return str.matches("[0-9]+");
        }else{
            return false;
        }
    }

    /**
     * 去除小数点后多余的0
     *
     * @param price 以分为单位的价格
     * @return
     */
    public static String getPriceSubZero(long price) {
        double priceW = price / 1000000d;
        NumberFormat formatter = new DecimalFormat("###,###.##");
        return formatter.format(priceW);
    }


    /**
     * 添加前後綴
     *
     * @param prefix
     * @param suffix
     * @return
     */
    public static Function getAppendPrefixSuffixFunction(final String prefix, final String suffix) {
        return new Function<String, String>() {
            @Override
            public String apply(String s) {
                return new StringBuilder().append(prefix).append(s).append(suffix).toString();
            }
        };
    }
}
