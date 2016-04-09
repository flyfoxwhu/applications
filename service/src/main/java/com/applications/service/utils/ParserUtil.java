package com.applications.service.utils;

import com.applications.common.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangwei on 16/1/26.
 */
public class ParserUtil {
    private static final String IMG_SPLIT = ";";

    private static final String DB_IMG_SPLIT = "\\#\\*\\#";//数据库里的分隔符是这个

    /**
     * 将英文分号隔开的多张图片，转化为list
     * @param imgs
     * @return
     */
    public static List<String> parseImgsToList(String imgs){
        if(StringUtil.isEmpty(imgs))    return null;
        String[] splits = imgs.split(IMG_SPLIT);
        List<String> lists = Arrays.asList(splits);
        return lists;
    }

    /**
     * 将;隔开的多张图片，转化为list
     * @param imgList
     * @return
     */
    public static String parseImgListToString(List<String> imgList){
        if(CollectionUtils.isEmpty(imgList))   return "";
        String imgs = StringUtil.join(imgList, IMG_SPLIT);
        return imgs;
    }

    /**
     * 原先约定的图片是用英文分号隔开的，后来变了，需要兼容
     * @param imgs
     * @return
     */
    public static String compatibleImgSplit(String imgs){
        if(StringUtil.isEmpty(imgs))    return  imgs;
        String newImgs = imgs.replaceAll(DB_IMG_SPLIT, IMG_SPLIT);
        return newImgs;
    }

    /**
     * 将String类型的时间转换为Long型
     * @param date
     * @return
     */
    public static Long dateStrToLong(String date){
        if (StringUtils.isBlank(date)){
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dat =null;
        try {
            dat = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dat==null){
            return null;
        }
        return dat.getTime();
    }

    /**
     * 将list Long转换成String
     * @param imgList
     * @return
     */
    public static String parseListToString(List<Long> imgList){
        if(CollectionUtils.isEmpty(imgList))   return "";
        String imgs = StringUtil.join(imgList, IMG_SPLIT);
        return imgs;
    }

    /**
     * 将;分割的String转换为List
     * @param str
     * @return
     */
    public static List<Long> parseStringToList(String str) {
        if (StringUtil.isEmpty(str)) return null;
        String[] splits = str.split(IMG_SPLIT);
        List<Long> lists = new ArrayList<>();
        for (int i = 0; i < splits.length; i++) {
            if (splits[i] != null) {
                lists.add(Long.valueOf(splits[i]));
            }
        }
        return lists;
    }

    public static void main(String[] args){
        String imgs = "https://dn-mhc.qbox.me/7ddfa2ea-cd12-4d95-bc9e-c6ae20d71deb.jpg;https://dn-mhc.qbox.me/80ad9a66-8e54-451d-9c67-6a56aea4eeb3.jpg;https://dn-mhc.qbox.me/7ddfa2ea-cd12-4d95-bc9e-c6ae20d71deb.jpg;https://dn-mhc.qbox.me/80ad9a66-8e54-451d-9c67-6a56aea4eeb3.jpg";
        List<String> imgList = parseImgsToList(imgs);
        String imgs2 = parseImgListToString(imgList);
        System.out.println();
        imgs = "https://dn-mhc.qbox.me/ceee10cc-d411-483e-872f-a340377e9dee.jpg#*#https://dn-mhc.qbox.me/560dc9df-6568-4e08-9e01-e0ef73c99efb.jpg";
        String newImgs = compatibleImgSplit(imgs);
        System.out.println();
    }
}
