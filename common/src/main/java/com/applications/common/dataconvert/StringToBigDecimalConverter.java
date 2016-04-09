package com.applications.common.dataconvert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

    @Override
    public BigDecimal convert(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }

        try {
            return new BigDecimal(param.trim());
        } catch (Exception e) {
            return null;
        }
    }
}
