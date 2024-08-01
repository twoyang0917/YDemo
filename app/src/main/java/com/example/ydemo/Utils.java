package com.example.ydemo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : md.digest()) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 加载本地库
    static {
        System.loadLibrary("utils");
    }

    public static native String getEncryptKey();

    public static native String encrypt1(String text);

    public static native String encrypt2(byte[] data);


    // c调用java的静态方法
    public static native String encrypt3(String text);

    // c调用java的成员方法
    private String name;
    public Utils(String name) {
        this.name = name;
    }
    public String showName() {
        return this.name;
    }

    public static native String getName();
}
