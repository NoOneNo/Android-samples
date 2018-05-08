package com.d.log

import android.util.Log

class AppLog {

    fun getStackTraceString(t:Throwable):String {
        return Log.getStackTraceString(t)
    }
}