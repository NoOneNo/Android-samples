package com.d.comp4.pm

// PackageManagerService启动

// SystemServer.main  SystemServer组件是由Zygote进程负责启动

// PackageManagerService.main

// ServiceManager.addService("package"

// 启动时安装这五个目录下的apk /system/framework  /system/app /vendor/app /data/app /data/app-private

// PackageParser.parsePackage

// new AssetManager() 解析 AndroidManifest.xml

// PackageManagerService.scanPackageLI

// mPackages.put mProvidersByComponent.put mServices.addService mReceivers.addActivity mActivities.addActivity 保存应用信息