package com.applications.service.sensitiveWord;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author hukaisheng
 * @date 2017/4/1.
 */
@Slf4j
class SensitiveWordInit {
    @SuppressWarnings("rawtypes")
    private HashMap sensitiveWordMap;

    SensitiveWordInit(){
        super();
    }

    @SuppressWarnings("rawtypes")
    Map initKeyWord(){
        try {
            //读取敏感词库
            Set<String> keyWordSet = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            addSensitiveWordToHashMap(keyWordSet);
            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            log.error("初始化敏感词库异常", e);
        }
        return sensitiveWordMap;
    }

    /**
     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
     * 中 = {
     *      isEnd = 0
     *      国 = {<br>
     *           isEnd = 1
     *           人 = {isEnd = 0
     *                民 = {isEnd = 1}
     *                }
     *           男  = {
     *                  isEnd = 0
     *                   人 = {
     *                        isEnd = 1
     *                       }
     *               }
     *           }
     *      }
     *  五 = {
     *      isEnd = 0
     *      星 = {
     *          isEnd = 0
     *          红 = {
     *              isEnd = 0
     *              旗 = {
     *                   isEnd = 1
     *                  }
     *              }
     *          }
     *      }
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addSensitiveWordToHashMap(Set<String> keyWordSet) {
        sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key;
        Map nowMap;
        Map<String, String> newWorMap;
        //迭代keyWordSet
        for (String aKeyWordSet : keyWordSet) {
            key = aKeyWordSet;    //关键字
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }


    /**
     * 读取敏感词库中的内容，将内容添加到set集合中
     */
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set;
        String t=Thread.currentThread().getContextClassLoader().getResource("").
                getPath()+"\\com\\risenb\\util\\";
        File file = new File(t+"SensitiveWord.txt");    //读取文件
        String ENCODING = "GBK";
        try (InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING)) {
            if (file.isFile() && file.exists()) {      //文件流是否存在
                set = new HashSet<>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt;
                while ((txt = bufferedReader.readLine()) != null) {    //读取文件，将文件内容放入到set中
                    set.add(txt);
                }
            } else {         //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        }
        return set;
    }

}
