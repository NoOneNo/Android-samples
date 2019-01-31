package com.d.webview

import android.annotation.TargetApi
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log


class KtorService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        Log.e("KtorFetchService", "onBind")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val HTTP_PORT = 7070
    override fun onCreate() {
        Log.e("KtorFetchService", "onCreate")

        KlServer().start()

        super.onCreate()
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun utf8Length(cs: CharSequence): Int {
        return cs.codePoints()
                .map { cp -> if (cp <= 0x7ff) if (cp <= 0x7f) 1 else 2 else if (cp <= 0xffff) 3 else 4 }
                .sum()
    }
}


/**
 *

POST / HTTP/1.1
Host: 127.0.0.1:7070
Content-Type: text/plain
Cache-Control: no-cache
Postman-Token: 4505ae34-290f-93fc-e149-8745d1922c5c

hello
 *
 * */



