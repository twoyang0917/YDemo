#include <jni.h>
#include <malloc.h>
#include <string.h>

// 静态注册
JNIEXPORT jstring
JNICALL
Java_com_example_ydemo_Utils_getEncryptKey(JNIEnv *env, jclass clazz) {
    char prefix[] = "ydemo";
    char suffix[] = "magic";

    // 字符串最后一位为'\0'
    char *key = (char *) malloc(strlen(prefix) + strlen(suffix) + 2);
    strcpy(key, prefix);
    strcat(key, "-");
    strcat(key, suffix);

    return (*env)->NewStringUTF(env, key);
}
