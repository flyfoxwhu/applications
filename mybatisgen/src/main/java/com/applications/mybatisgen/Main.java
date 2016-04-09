package com.applications.mybatisgen;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        // 需要生成的表配置文件在generate_tables.config文件中，每每行一个表名
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("generate_tables.config");
        List<String> tables = IOUtils.readLines(inputStream);
        if (CollectionUtils.isEmpty(tables)) {
            System.out.println("generate_tables.config is empty.");
            return;
        }
        MyBatisGenCore.batchGen(tables);
        System.out.print("all done");
    }

}