#include <jni.h>
#include <string.h>
#include <syslog.h>
#include <malloc.h>



// 动态注册
jstring native_encrypt1(JNIEnv *env, jobject obj, jstring text) {
    char *pText = (*env)->GetStringUTFChars(env, text, 0);
    char *pEncrypt = (char *) malloc(strlen(pText) * 2 + 1);
    for (int i = 0; i < strlen(pText); i++) {
        sprintf(pEncrypt + i * 2, "%02x", pText[i]);
    }
    return (*env)->NewStringUTF(env, pEncrypt);
}

jstring native_encrypt2(JNIEnv *env, jobject obj, jbyteArray data) {
    // 将byteArr转换为16进制字符串
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

static const JNINativeMethod methods[] = {
        {"encrypt1", "(Ljava/lang/String;)Ljava/lang/String;", (void *) native_encrypt1},
        {"encrypt2", "([B)Ljava/lang/String;", (void *) native_encrypt2}
};

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved) {
    // 固定代码
    JNIEnv* env = NULL;

    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    // 获取类
    jclass clazz = (*env)->FindClass(env, "com/example/ydemo/Dynamic");
    if (clazz == NULL) return JNI_ERR;

    // 注册方法 最后一个参数是方法个数
    int rc = (*env)->RegisterNatives(env, clazz, methods, sizeof(methods)/sizeof(JNINativeMethod));
    if (rc != JNI_OK) return rc;

    return JNI_VERSION_1_6;
}
