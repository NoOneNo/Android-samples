package com.d.comp4.service

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder

/**
 * Created by Harry on 2018/6/30.
 */

class CustomService : Service() {
    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getService() {

        // ServiceManager.getService("AnonymousSharedMemory")

        // IMemoryService.Stub.asInterface

        // SystemServer 随机启动 如 ActivityManagerService

        // ActivityManagerService.main

        // ActivityManagerService.setSystemProcess 添加到ServiceManager中

        // ServiceManager.addService
    }

    override fun startService(service: Intent?): ComponentName {

        // ContextWrapper.startService

        // ContextImpl.startService

        // ActivityManagerProxy ActivityManagerService

        // ActivityManagerService.startProcessLocked

        // Process.start

        // ActivityThread.main

        // Looper.loop()

        return super.startService(service)
    }
}