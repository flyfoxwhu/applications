package com.applications.service.crawl.service;

import net.sf.json.JSONArray;

/**
 * Created by hukaisheng on 16/4/10.
 */
public interface CrawlService {

    /***
     * 汽车之家的首页信息
     */
    void crawlAutoHome();

    /**
     * 颜色图片
     * @param color
     * @return
     */
    String getColor(String color);

    /**
     * 颜色图片
     * @param colors
     * @return
     */
    String getColor2(JSONArray colors);

    /**
     * 车型图片
     * @param start
     * @param end
     */
    void getModelPictures(Integer start,Integer end);
}
