package com.applications.common.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ImgZipUtil {


    /**
     * 批量将图片打成一个zip包
     * @param urls
     * @param tempPath
     * @param zipName
     * @return
     * @throws Exception
     */
    public static String exportZipImg(List<String> urls, String tempPath, String zipName) throws Exception {
        if (CollectionUtils.isEmpty(urls)) {
            return zipName;
        }
        zipName=zipName+".zip";
        ZipOutputStream out = null;
        try {
            byte[] buffer = new byte[1024];
            String strZipName = tempPath + File.separator + zipName;
            out = new ZipOutputStream(new FileOutputStream(strZipName));
            for (String url : urls) {
                if (StringUtils.isBlank(url)) {
                    continue;
                }
                InputStream fis = urlToInputStream(url);
                if(fis==null){
                    continue;
                }
                out.putNextEntry(new ZipEntry(url.substring(url.lastIndexOf("/"))));
                int len;
                //打包到zip文件
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return zipName;
    }


    /**
     * 将图片url转为inputStream
     * @param urlString
     * @return
     */
    public static InputStream urlToInputStream(String urlString) {
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            InputStream is = con.getInputStream();
            return is;
        } catch (IOException e) {
            return null;
        }
    }



    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("https://dn-mhc.qbox.me/03B6203E-6C79-495E-9AEA-243864FE99E2.jpg34");
        list.add("https://dn-mhc.qbox.me/7863C8AB-EA69-4D01-96DD-6BA74D39F947.jpg");
        String s="";
        try {
             s=ImgZipUtil.exportZipImg(list,"/Users/weishuai/mhc_tmp","898989898989");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("生成"+s+"成功");

    }
}



