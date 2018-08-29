package com.popular.movies.util

import android.util.Log
import com.popular.movies.BuildConfig


object AppLog {
    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.d(tag, message)
    }

    fun d(message: String) {
        if (BuildConfig.DEBUG) Log.d("debug", message)
    }

    fun i(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.i(tag, message)
    }

    fun i(message: String) {
        if (BuildConfig.DEBUG) Log.i("info", message)
    }

    fun e(tag: String, message: String) {
        if (BuildConfig.DEBUG) Log.e(tag, message)
    }

    fun e(message: String) {
        if (BuildConfig.DEBUG) Log.e("error", message)
    }
}
