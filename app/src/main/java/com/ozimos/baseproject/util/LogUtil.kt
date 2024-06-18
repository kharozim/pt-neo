package com.ozimos.baseproject.util

import android.util.Log

object LogUtil {
    private const val TAG = "LogUtil"

    var isDebugEnabled = true
    var isInfoEnabled = true
    var isWarnEnabled = true
    var isErrorEnabled = true

    fun d(message: String, tag: String = TAG) {
        if (isDebugEnabled) {
            Log.d(tag, message)
        }
    }

    fun i(message: String, tag: String = TAG) {
        if (isInfoEnabled) {
            Log.i(tag, message)
        }
    }

    fun w(message: String, tag: String = TAG) {
        if (isWarnEnabled) {
            Log.w(tag, message)
        }
    }

    fun e(message: String, tag: String = TAG, throwable: Throwable? = null) {
        if (isErrorEnabled) {
            if (throwable != null) {
                Log.e(tag, message, throwable)
            } else {
                Log.e(tag, message)
            }
        }
    }
}