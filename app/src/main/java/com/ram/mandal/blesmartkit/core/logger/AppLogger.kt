package com.ram.mandal.blesmartkit.core.logger

import android.util.Log

class AppLogger : Logger {
    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }
}