package com.ozimos.baseproject

import android.app.Application
import com.ozimos.baseproject.util.LogUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set log levels based on build type or other conditions
        if (BuildConfig.DEBUG) {
            LogUtil.isDebugEnabled = true
            LogUtil.isInfoEnabled = true
            LogUtil.isWarnEnabled = true
            LogUtil.isErrorEnabled = true
        } else {
            LogUtil.isDebugEnabled = false
            LogUtil.isInfoEnabled = true
            LogUtil.isWarnEnabled = true
            LogUtil.isErrorEnabled = true
        }
    }
}