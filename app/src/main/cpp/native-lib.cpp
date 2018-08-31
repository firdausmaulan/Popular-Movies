#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring

JNICALL
Java_com_popular_movies_util_AppSecret_stringFromJNI(JNIEnv *env, jobject /* this */) {
    std::string api_key = "6cb025fee4cbfb787415c63d5fa87583";
    return env->NewStringUTF(api_key.c_str());
}
