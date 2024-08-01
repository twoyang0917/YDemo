package com.example.ydemo;

public class Dynamic {
    static {
        System.loadLibrary("dynamic");
    }
    public static native String encrypt1(String text);
    public static native String encrypt2(byte[] data);
}
