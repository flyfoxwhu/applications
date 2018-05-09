package com.applications.service.strategy;

/**
 * @author hukaisheng
 * @date 2018/5/9.
 */
@PriceRegion(max = 10000)
public class Orgnic implements CalPrice {

    @Override
    public Double calPrice(Double orgnicPrice) {
        return orgnicPrice;
    }
}
