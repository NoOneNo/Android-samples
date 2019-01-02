package com.d.framework.activityThread

//import android.app.ActivityThread
import android.content.pm.PackageManager

class ActivityThreadCustom {
    fun getAT() {

        // ActivityThread 是hide类

        // Android P 限制 1.反射 JNI 获取方法属性, art/runtime/hidden_api.h 进制, 2. BootStrapClassLoader判断是不是系统类的调用

        // 绕过限制的方案, 直接调用, https://juejin.im/post/5acf3be5f265da23a40534f4

        // https://developer.android.com/preview/restrictions-non-sdk-interfaces?hl=zh-cn

//        ActivityThread.currentActivityThread()
//
//        ActivityThread.currentApplication()
//
//        ActivityThread.getPackageManager()

        // 可以通过resID得到其他app的资源
        // (ActivityThread.getPackageManager() as PackageManager).getText()
        // getResourcesForApplication

        // https://blog.csdn.net/maplejaw_/article/details/51530442 Android插件化探索（三）免安装运行Activity（上）
    }
}
