package com.applications.service.crawl.service.impl;

import com.applications.service.crawl.service.CrawlService;
import com.applications.service.utils.ThreadPoolFactory;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.List;

@Service("crawlService")
public class CrawlServiceImpl implements CrawlService {

    private static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Integer INNER_COLOR_TYPE = 0;//内饰
    public static Integer OUTER_COLOR_TYPE = 1;//外观


    private static String readUrl(String urlString) throws Exception {
        if(urlString.startsWith("https://")){
            urlString = urlString.replaceFirst("https","http");
        }
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间为10000ms
            conn.setConnectTimeout(100000);
            // 设置读取数据超时时间为10000ms
            conn.setReadTimeout(100000);
            setHeader(conn);
            //获取文件输入流，读取文件内容
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "GB2312"));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }



    public  void crawlAutoHome(){
        String lookUrl = "http://car.autohome.com.cn/zhaoche/pinpai/";
        String strResult = null;
        try {
            strResult = readUrl(lookUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(strResult);
        Elements divs = doc.getElementsByClass("uibox-con");
        for(Element div:divs){
            for(Element dl:div.children()){
                Element dt = dl.child(0);
                Element p = dt.child(1);
                String brand = p.text();
                Element dd = dl.child(1);
                Elements h3s = dd.select("h3");
                if(h3s.size()>0){
                    for(Element h3:h3s){
                        String factory = h3.child(0).text();
                        Element ul = h3.nextElementSibling();
                        Elements lis = ul.select("li");
                        for(Element li:lis){
                            Element h4 = li.child(0);
                            String series = h4.text();
                            Element divMore = li.child(1);
                            Element price = divMore.child(0);
                            String priceString = "";
                            if(price.nodeName().equals("span")){
                                priceString = "无";
                            }else if(price.nodeName().equals("a")){
                                String html = price.attr("href");
                                String strResultHtml = null;
                                try {
                                    strResultHtml = readUrl(html);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Document docHtml = Jsoup.parse(strResultHtml);
                                Elements spans = docHtml.getElementsByClass("lever-price");
                                priceString = spans.get(0).child(0).text();
                            }
                            String crawlUrl = "无";
                            String url = h4.child(0).attr("href");
                            String strResultUrl = null;
                            try {
                                strResultUrl = readUrl(url);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Document docHtml = Jsoup.parse(strResultUrl);
                            Element divUrl = docHtml.getElementById("navTop");
                            if(divUrl!=null){
                                Elements lisUrl = divUrl.select("li");
                                Element liUrl = lisUrl.get(1);
                                if(liUrl.child(0).nodeName().equals("span")){

                                }else if(liUrl.child(0).nodeName().equals("a")){
                                    crawlUrl = liUrl.child(0).attr("href");
                                }
                            }
                            //插入到表中省略...
                        }
                    }
                }else {
                    Elements lis = dd.select("li");
                    for(Element li:lis){
                        Element h4 = li.child(0);
                        String series = h4.text();
                        Element divMore = li.child(1);
                        Element price = divMore.child(0);
                        String priceString = "";
                        if(price.nodeName().equals("span")){
                            priceString = "无";
                        }else if(price.nodeName().equals("a")){
                            String html = price.attr("href");
                            String strResultHtml = null;
                            try {
                                strResultHtml = readUrl(html);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Document docHtml = Jsoup.parse(strResultHtml);
                            Elements spans = docHtml.getElementsByClass("lever-price");
                            priceString = spans.get(0).child(0).text();
                        }
                        String crawlUrl = "无";
                        String url = h4.child(0).attr("href");
                        String strResultUrl = null;
                        try {
                            strResultUrl = readUrl(url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Document docHtml = Jsoup.parse(strResultUrl);
                        Element divUrl = docHtml.getElementById("navTop");
                        if(divUrl!=null){
                            Elements lisUrl = divUrl.select("li");
                            Element liUrl = lisUrl.get(1);
                            if(liUrl.child(0).nodeName().equals("span")){

                            }else if(liUrl.child(0).nodeName().equals("a")){
                                crawlUrl = liUrl.child(0).attr("href");
                            }
                        }
                        //插入到表中省略...

                    }
                }
            }
        }
    }

    private static String getYear(String name){
        String[] names = name.split(" ");
        String seriesName = names[0];
        String year = names[1];
        String modelName = names[2];
        return year;
    }

    public static void setHeader(URLConnection conn) {
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:26.0) Gecko/20100101 Firefox/26.0");
        conn.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
        conn.setRequestProperty("Accept-Encoding", "utf-8");
        conn.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
        conn.setRequestProperty("Keep-Alive", "300");
        conn.setRequestProperty("connnection", "keep-alive");
        conn.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
        conn.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
        conn.setRequestProperty("Cache-conntrol", "max-age=0");
    }

    //把字符串表达的颜色值转换成java.awt.Color
    public static Color parseToColor(final String c) {
        Color convertedColor = Color.ORANGE;
        try {
            convertedColor = new Color(Integer.parseInt(c, 16));
        } catch(NumberFormatException e) {
            // codes to deal with this exception
        }
        return convertedColor;
    }

    public String getColor(String color){
        String colorUrl = "";
        color = color.replaceAll("#","");
        Color javaColor = parseToColor(color);
        BufferedImage buffImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        //设定背景色
        gd.setColor(javaColor);
        gd.fillRect(0, 0, 30, 30);
        String key = "/tmp/"+UUID.randomUUID().toString().toUpperCase() + ".png";
        File imgFile=new File(key);
        try
        {
            ImageIO.write(buffImg, "PNG", imgFile);
            InputStream input = new FileInputStream(imgFile);
            byte[] byt = new byte[input.available()];
            input.read(byt);
            colorUrl = null/*imageManager.uploadFile(byt, ".png")*/;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return colorUrl;
    }

    public String getColor2(JSONArray colors){
        String colorUrl = "";
        if(colors.size()==1){
            String color = colors.get(0).toString();
            color = color.replaceAll("#","");
            Color javaColor = parseToColor(color);
            BufferedImage buffImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            //设定背景色
            gd.setColor(javaColor);
            gd.fillRect(0, 0, 30, 30);
            String key = "/tmp/"+UUID.randomUUID().toString().toUpperCase() + ".png";
            File imgFile=new File(key);
            try
            {
                ImageIO.write(buffImg, "PNG", imgFile);
                InputStream input = new FileInputStream(imgFile);
                byte[] byt = new byte[input.available()];
                input.read(byt);
                //这里是获取图片上传到文件服务器的地址
                colorUrl = null;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        if(colors.size()==2){
            String color = colors.get(0).toString();
            String color2 = colors.get(1).toString();
            color = color.replaceAll("#","");
            color2 = color2.replaceAll("#","");
            Color javaColor = parseToColor(color);
            Color javaColor2 = parseToColor(color2);
            BufferedImage buffImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
            Graphics2D gd = buffImg.createGraphics();
            //设置透明  start
            buffImg = gd.getDeviceConfiguration().createCompatibleImage(30, 30, Transparency.TRANSLUCENT);
            gd=buffImg.createGraphics();
            //设置透明  end
            gd.setColor(javaColor); //设置颜色
            gd.fillRect(0, 0, 15, 30); //画边框
            gd.setColor(javaColor2); //设置颜色
            gd.fillRect(15, 0, 30, 30); //画边框
            String key = "/tmp/"+UUID.randomUUID().toString().toUpperCase() + ".png";
            File imgFile=new File(key);
            try
            {
                ImageIO.write(buffImg, "PNG", imgFile);
                InputStream input = new FileInputStream(imgFile);
                byte[] byt = new byte[input.available()];
                input.read(byt);
                //这里是获取图片上传到文件服务器的地址
                colorUrl = null;
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return colorUrl;
    }

    /**
     * 获取汽车之家的车型图片：
     * 1从mhc_auto中获取所有的车系配置页面url
     * 2在车系配置页面找到所有的外观颜色和内饰颜色访问的url
     * 3在外观颜色和内饰颜色的页面找到所有图片类型的访问的url
     * 4在图片类型页面找到所有的图片插入车型图片表
     * 5start,end是mhc_auto中车系的配置的页面的起止个数
     */
    public void getModelPictures(Integer start,Integer end) {

        List<String> pvUrls=new ArrayList<String>();
        //获取所有的车系配置页面url
        //修正起至值
        if (start == null || start < 0) {
            start = 0;
        }
        if (end == null || end > pvUrls.size()) {
            end = pvUrls.size();
        }
        if (start > end) {
            return;
        }
        log.info("开始爬取图片");
        for (int i = start; i < end; i++) {
            //在车系配置页面找到所有的车型、外观颜色和内饰颜色组合的url
            ModelImageWork imageWork = new ModelImageWork(pvUrls.get(i));
            ThreadPoolFactory.getThreadPool().execute(imageWork);
        }
        log.info("爬取图片结束");

    }

    /**
     * 在车系配置页面找到所有的车型、外观颜色和内饰颜色组合的url
     * 图片的数量要大于0
     *
     * @param url
     */
    private void insertModelColorUrl(String url) {
        String strResult = null;
        try {
            strResult = readUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(strResult);
        Elements tds = doc.select("script");
        for (Element td : tds) {
            if (td.html().contains("var dealerPrices")) {
                //获取车系配置下的所有车型
                Map<String, Long> modelMap = getModelMap(td);
                //获取外观类型的url和颜色
                insertModelColorTypeUrl(td, "var color = ", "var innerColor=", OUTER_COLOR_TYPE, modelMap);
                //获取内饰类型的url和颜色
                insertModelColorTypeUrl(td, "var innerColor=", "var dealerPrices ", INNER_COLOR_TYPE, modelMap);
            }
        }
    }

    /**
     * 获取车系配置下的所有车型
     * 将汽车之家的车型id与我们的车型ID进行映射
     *
     * @param td
     * @return
     */
    private Map<String, Long> getModelMap(Element td) {
        Map<String, Long> modelMap = new HashMap<String, Long>();
        String beginStr = "var config = ";
        int beginConfig = td.html().indexOf(beginStr);
        int beginOption = td.html().indexOf("var option = ");
        String config = td.html().substring(beginConfig + beginStr.length(), beginOption - 11);
        JSONObject configJson = JSONObject.fromObject(config);
        JSONObject resultConfigJson = JSONObject.fromObject(configJson.get("result"));
        JSONArray paramtypeitems = JSONArray.fromObject(resultConfigJson.get("paramtypeitems"));
        for (Object paramtypeitem : paramtypeitems) {
            JSONObject jsonParamtypeitem = JSONObject.fromObject(paramtypeitem);
            JSONArray paramitems = JSONArray.fromObject(jsonParamtypeitem.get("paramitems"));
            for (Object paramitem : paramitems) {
                JSONObject jsonParamitem = JSONObject.fromObject(paramitem);
                String nameItem = jsonParamitem.get("name").toString();//二级propertyName
                JSONArray valueitems = JSONArray.fromObject(jsonParamitem.get("valueitems"));
                for (Object valueItem : valueitems) {
                    JSONObject jsonValueItem = JSONObject.fromObject(valueItem);
                    String specId = jsonValueItem.get("specid").toString();
                    String value = jsonValueItem.get("value").toString();
                    if (nameItem.equals("车型名称")) {

                    }
                }
            }
        }

        return modelMap;
    }

    /**
     * 根据外观或内饰，传入颜色和url
     *
     * @param td
     * @param start
     * @param end
     * @param colorType 0内饰 1外观
     */
    private void insertModelColorTypeUrl(Element td, String start, String end, Integer colorType, Map<String, Long> modelMap) {
        int beginColor = td.html().indexOf(start);
        int begindealerPrices = td.html().indexOf(end);
        //获取页面中color的json
        String colorJsonStr = td.html().substring(beginColor + start.length(), begindealerPrices - 11);
        JSONObject colorJson = JSONObject.fromObject(colorJsonStr);
        JSONObject resultColorJson = JSONObject.fromObject(colorJson.get("result"));
        JSONArray specJsons = JSONArray.fromObject(resultColorJson.get("specitems"));
        for (Object specJson:specJsons){
            JSONObject specJsonItem = JSONObject.fromObject(specJson);
            String specId = specJsonItem.get("specid").toString();
            JSONArray items = JSONArray.fromObject(specJsonItem.get("coloritems"));
            //外观类的图片
            for (Object item : items) {
                JSONObject jsonItem = JSONObject.fromObject(item);
                String colorId = jsonItem.get("id").toString();
                Integer picnum = Integer.valueOf(jsonItem.get("picnum").toString()) ;
                String colorName = jsonItem.get("name").toString();

                //图片数量大于0的才保存下来
                if (picnum > 0) {
                    String modelColorUrl = "http://car.autohome.com.cn/pic/spec-" + specId + "-" + colorId + ".html";
                    if (modelMap.get(specId) == null) {
                        continue;
                    }
                    int num = insertModelColorTypeUrl(modelColorUrl, colorName, colorType, modelMap.get(specId));
                    //爬取非常规的url页面
                    if (num == 0){
                        modelColorUrl = "http://car.autohome.com.cn/pic/spec-" + specId + "-i" + colorId + ".html";
                        insertModelColorTypeUrl(modelColorUrl, colorName, colorType, modelMap.get(specId));
                    }
                }
            }
        }

    }

    /**
     * 在车型、颜色的页面找到所有的外观颜色、内饰颜色和图片类型组合的url
     *
     * @return
     */
    private int insertModelColorTypeUrl(String modelColorUrl, String colorName, Integer colorType, Long modelId) {
        int num = 0;
        if (modelId == null) {
            return num;
        }
        String strResult = null;
        try {
            strResult = readUrl(modelColorUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc = Jsoup.parse(strResult);
        Elements tds = doc.getElementsByClass("uibox-title");
        for (Element td : tds) {
            for (Element child : td.children()) {
                if (child.nodeName().equals("a") && child.hasClass("more")) {
                    String href = child.attr("href");
                    if (href.indexOf("/pic/spec-") < 0) {
                        continue;
                    }
                    num++;
                    String modelColorTypeUrl = "http://car.autohome.com.cn" + href;
                    addModelPicture(modelColorTypeUrl, colorName, colorType, modelId);
                }
            }

        }

        return num;
    }

    /**
     * 根据外观颜色、内饰颜色和图片类型组合的url
     * 获取车型、图片类型、内饰外观颜色，所有的图片插入车型图片表
     */
    private void addModelPicture(String modelColorTypeUrl, String colorName, Integer colorType, Long modelId) {
        String strResult = null;
        try {
            strResult = readUrl(modelColorTypeUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Document doc = Jsoup.parse(strResult);
        //获取该车型、外观或内饰、颜色的所有图片
        Elements tds = doc.getElementsByClass("uibox-title");

        Elements imgs = doc.getElementsByTag("img");
        for (Element img : imgs) {
            String imagUrl = img.attr("src");
            if (imagUrl == null || imagUrl.indexOf("logo") >= 0) {
                continue;
            }
            //直接存储
            if (imagUrl.indexOf("t_") >= 0) {
                imagUrl = imagUrl.replace("t_", "");
            }
            //上传并存储，上线之后执行
            //modelPictureDO.setPictureUrl(getRealImageUrl(imagUrl));
        }


        //存在分页的情况下，爬取下一页的图片
        Elements nextPages = doc.getElementsByClass("page-item-next");
        for(Element nextPage:nextPages){
            String nextUrl = nextPage.attr("href");
            if (nextUrl.indexOf("spec-") >= 0){
                String nextPageUrl = "http://car.autohome.com.cn" + nextUrl;
                addModelPicture(nextPageUrl, colorName, colorType, modelId);
            }
        }

    }

    /**
     * 上传到服务器返回url
     * @param url
     * @return
     */
    private String getRealImageUrl(String url){
        //上传到服务器返回url
        ByteArrayOutputStream baos = null;
        String realUrl= null;
        try {
            URL u = new URL(url);
            BufferedImage image = ImageIO.read(u);
            baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            baos.flush();
            //获取图片上传之后的地址
            realUrl = null/*imageManager.uploadFile(baos.toByteArray())*/;
        } catch (IOException e) {
            log.error("upload picture error",e);
        }finally {
            try {
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return realUrl == null ? url : realUrl;
    }

    class ModelImageWork implements Runnable {
        private String pvUrl;

        ModelImageWork(String pvUrl) {
            this.pvUrl = pvUrl;
        }

        @Override
        public void run() {
            insertModelColorUrl(pvUrl);
        }
    }


}
