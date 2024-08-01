#include <jni.h>
#include <malloc.h>
#include <string.h>
#include <syslog.h>

// 静态注册
JNIEXPORT jstring JNICALL
Java_com_example_ydemo_Utils_getEncryptKey(JNIEnv *env, jclass clazz) {
    // 拼接字符串
    char prefix[] = "ydemo";
    char suffix[] = "magic";

    // 字符串最后一位为'\0'
    char *key = (char *) malloc(strlen(prefix) + strlen(suffix) + 2);
    strcpy(key, prefix);
    strcat(key, "-");
    strcat(key, suffix);

    return (*env)->NewStringUTF(env, key);
}

JNIEXPORT jstring JNICALL
Java_com_example_ydemo_Utils_encrypt1(JNIEnv *env, jclass clazz, jstring text) {
    // 转换为16进制字符串
    char *pText = (*env)->GetStringUTFChars(env, text, 0);
    char *pEncrypt = (char *) malloc(strlen(pText) * 2 + 1);
    for (int i = 0; i < strlen(pText); i++) {
        sprintf(pEncrypt + i * 2, "%02x", pText[i]);
    }
    return (*env)->NewStringUTF(env, pEncrypt);
}

JNIEXPORT jstring JNICALL
Java_com_example_ydemo_Utils_encrypt2(JNIEnv *env, jclass clazz, jbyteArray data) {
    // 转换为16进制字符串
    jbyte *pData = (*env)->GetByteArrayElements(env, data , 0);
    int size = (*env)->GetArrayLength(env, data);

    char cipher[size * 2 + 1];
    char *pEncrypt = cipher;

    for (int i = 0; pData[i] != '\0'; i++) {
        syslog(LOG_ERR, "%02x", pData[i]);
        sprintf(pEncrypt, "%02x", pData[i]);
        pEncrypt += 2;
    }
    return (*env)->NewStringUTF(env, cipher);
}

JNIEXPORT jstring JNICALL
Java_com_example_ydemo_Utils_encrypt3(JNIEnv *env, jclass clazz, jstring text) {
    // 调用java中的md5加密方法
    jclass cls = (*env)->FindClass(env, "com/example/ydemo/Utils");
    // 获取md5方法，有重载函数的话，通过第三个参数函数签名来区分
    // jmethodID md5 = (*env)->GetStaticMethodID(env, cls, "md5", "([B)Ljava/lang/String;");
    jmethodID md5 = (*env)->GetStaticMethodID(env, cls, "md5", "(Ljava/lang/String;)Ljava/lang/String;");
    return (*env)->CallStaticObjectMethod(env, cls, md5, text);
}

JNIEXPORT jstring JNICALL
Java_com_example_ydemo_Utils_getName(JNIEnv *env, jobject clazz) {
    jclass cls = (*env)->FindClass(env, "com/example/ydemo/Utils");
    // 获取构造方法
    jmethodID init = (*env)->GetMethodID(env, cls, "<init>", "(Ljava/lang/String;)V");
    // 获取成员方法
    jmethodID showName = (*env)->GetMethodID(env, cls, "showName", "()Ljava/lang/String;");
    // 实例化对象
    jobject classObj = (*env)->NewObject(env, cls, init, (*env)->NewStringUTF(env, "ydemo"));
    // 调用方法
    return (*env)->CallObjectMethod(env, classObj, showName);
}
