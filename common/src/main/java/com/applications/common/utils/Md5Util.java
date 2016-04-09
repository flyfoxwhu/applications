package com.applications.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    private final static String KEY = "woshimaihaoche";

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'a','b','c','d','e','f' };
        char[] resultCharArray =new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        return new String(resultCharArray);
    }

    /**
     * 单纯的md5加密
     * @param data
     * @return
     */
    public static String simpleStringMD5(String data) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String stringMD5(String data) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            data += KEY;
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String md5Oil(Long oid,Long cid) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            String data = oid + KEY_OIL + cid;
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static String checkId1(String itemId) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            String data = D11_CHECK1 + itemId;
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static String checkId2(String itemId) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            String data = D11_CHECK2 + itemId;
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    public static String checkId3(String itemId) {
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");
            String data = D11_CHECK3 + itemId;
            byte[] inputByteArray = data.getBytes();
            messageDigest.update(inputByteArray);
            byte[] resultByteArray = messageDigest.digest();
            return byteArrayToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    private final static String KEY_OIL = "double11filloil";
    private final static String D11_CHECK1 = "DoublE11Chk11XX";
    private final static String D11_CHECK2 = "DoublE11Chk22XX";
    private final static String D11_CHECK3 = "Dou33E11Chk22XX";


    public static void main(String args[]){
        System.out.println(Md5Util.simpleStringMD5("hello1234"));
    }

}
