package com.applications.service.strategy;

/**
 * @author hukaisheng
 * @date 2018/5/9.
 */
@PriceRegion(min=20000,max=30000)
public class SuperVip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.8;
    }
}