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
}
