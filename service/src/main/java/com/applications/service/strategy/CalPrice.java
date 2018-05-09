package com.applications.service.strategy;

/**
 * @author hukaisheng
 * @date 2018/5/9.
 */
public interface CalPrice {

    //根据原价返回一个最终的价格
    Double calPrice(Double orgnicPrice);
}
