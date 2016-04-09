package com.applications.common.dataconvert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToIntegerConverter implements Converter<String, Integer> {

    @Override
    public Integer convert(String param) {
        if (StringUtils.isBlank(param)) {
            return null;
        }

        try {
            return Integer.parseInt(param);
        } catch (Exception e) {
            return null;
        }
    }

}
