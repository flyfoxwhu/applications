package com.applications.service.strategy;

/**
 * @author hukaisheng
 * @date 2018/5/9.
 */
@PriceRegion(min=10000,max=20000)
public class Vip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.9;
    }
}
