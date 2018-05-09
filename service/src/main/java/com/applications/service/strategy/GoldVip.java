package com.applications.service.strategy;

/**
 * @author hukaisheng
 * @date 2018/5/9.
 */
@PriceRegion(min=30000)
public class GoldVip implements CalPrice {
    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice * 0.7;
    }
}
