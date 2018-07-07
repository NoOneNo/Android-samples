package com.d.comp4.activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent


class CustomActivity : Activity() {


    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)

        // mInstrumentation - Instrumentation execStartActivity
        // mMainThread - ActivityThread - getApplicationThread - ApplicationThread
        // ApplicationThread 是一个Binder代理对象
        // mToken 是一个Binder代理对象 -> resultTo
    }


}

class CustomInstrumentation : Instrumentation() {
    fun execStartActivity() {

        // ActivityManagerNative.getDefault() 得到ActivityManagerService的代理对象ActivityManagerProxy

        // 进入system进程

        // ActivityManagerService.startActivity

        // ActivityStack.startActivityMayWait

        // AppGlobals.getPackageManager().resolveIntent

        // ActivityStack.startActivityLocked

        // resultTo -> sourceRecord (ActivityRecord)

        // intent -> new ActivityRecord

        // ActivityStack.startActivityUncheckedLocked

        // r.task = new TaskRecord

        // ActivityStack.resumeTopActivityLocked

        // ActivityStack.startPausingLocked

        // 进入调用者进程

        // ApplicationThreadProxy.schedulePauseActivity

        // 进入system进程

        // ActivityManagerService.activityPaused

        // ActivityStack.activityPaused

        // ActivityManagerService.startProcessLocked

        // new ProcessRecordLocked

        // Process.start

        // 进入被调用者进程

        // ActivityThread.main

        // new ActivityThread() -- final 类

        // ActivityManagerProxy.attachApplication -- ApplicationThread类型的Binder对象

        // ActivityManagerService.attachApplicationLocked

        // pid -> ProcessRecord app

        // ActivityStack.realStartActivityLocked

        // ApplicationThreadProxy.scheduleLaunchActivity

        // ActivityRecord -> Token binder对象

        //  ActivityThread.queueOrSendMessage

        // H.handleMessage

        // ActivityThread.handleLaunchActivity

        // ActivityThread.performLaunchActivity

        // r.packageInfo.getClassLoader()

        // mInstrumentation.newActivity

        // r.packageInfo.makeApplication

        // activity.attach

        // mInstrumentation.callActivityOnCreate
    }
}