package com.applications.common.dataconvert;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringToDateConverter implements Converter<String, Date> {
    @SuppressWarnings("serial")
    private List<String> format = new ArrayList<String>() {
    };

    public StringToDateConverter() {
        init();
    }

    @Override
    public Date convert(String src) {
        if (src == null || "".equals(src.trim())) {
            return null;
        }
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        for (String fm : format) {
            simpleDateFormat.applyPattern(fm);
            try {
                date = simpleDateFormat.parse(src);
            } catch (ParseException e) {
                date = null;
            }
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    public void setFormat(List<String> format) {
        if (format != null && !format.isEmpty()) {
            for (String fm : format) {
                if (!this.format.contains(fm)) {
                    this.format.add(fm);
                }
            }
        }
    }

    private void init() {
        format.add("yyyy-MM-dd HH:mm:ss");
        format.add("yyyy-MM-dd HH:mm");
        format.add("yyyy-MM-dd");
        format.add("yyyyMMddHHmmss");
        format.add("yyyyMMdd");
        format.add("yyyy年MM月dd日");
        format.add("yyyyMMddHHmm");
        format.add("yyyy年MM月dd日HH时mm分ss秒");
        format.add("yyyy-MM-dd HH:mm:ss:SSS");
        format.add("HH:mm:ss");
        format.add("yyyy");
        format.add("yyyy-MM");
    }

}
