package com.d.framework.zygote

// Zygote进程也不例外，它是在系统启动的过程，由init进程创建的。在系统启动脚本system/core/rootdir/init.rc文件中
// service zygote /system/bin/app_process -Xzygote /system/bin --zygote --start-system-server

// socket zygote stream 666 -- 系统启动后，我们就可以在/dev/socket目录下看到有一个名为zygote的文件
// 《Linux内核源代码情景分析》的第七章--基于socket的进程间通信

// app_process.main

// AndroidRuntime.start

    // startVM启动虚拟机

    // startReg注册JNI方法

    // 调用了com.android.internal.os.ZygoteInit类的main函数

// ZygoteInit.main

    // 调用registerZygoteSocket函数创建了一个socket接口，用来和ActivityManagerService通讯

    // 调用startSystemServer函数来启动SystemServer组件

    // 调用runSelectLoopMode函数进入一个无限循环在前面创建的socket接口上等待ActivityManagerService请求创建新的应用程序进程