https://blog.csdn.net/luoshengyang/article/details/45601143
Android应用程序UI硬件加速渲染技术简要介绍和学习计划
+ 3.0前，Android应用程序UI绘制不支持硬件加速 (2D)
+ 第一步是在Android应用程序进程 -- 将UI绘制一个图形缓冲区中，并且将该图形缓冲区交给后一步
+ 第二步是在SurfaceFlinger进程 -- 以硬件加速方式完成

https://blog.csdn.net/luoshengyang/article/details/7691321 --- 1
Android系统的开机画面显示过程分析
## 
+ 第一个画面 - 内核启动
+ 第二个画面 - init进程启动
+ 第三个画面 - 系统服务启动 动态
+ 帧缓冲区（frame buffer，简称fb）的硬件设备上进行渲染

## fbmem
+ fb - 驱动程序模块fbmem - `kernel/goldfish/drivers/video/fbmem.c`
+ proc_create 在 /proc目录 创建了一个fb文件
+ register_chrdev来注册了一个名称为fb的字符设备
+ class_create在/sys/class目录下创建了一个graphics目录，用来描述内核的图形系统
+ 数组registered_fb保存所有已经注册了的帧缓冲区硬件设备，其中，每一个帧缓冲区硬件都是使用一个结构体fb_info来描述的
+ Linux内核中，每一个硬件设备都有一个主设备号和一个从设备号，它们用来唯一地标识一个硬件设备
+ 帧缓冲区硬件设备来说，它们的主设备号定义为FB_MAJOR（29），而从设备号则与注册的顺序有关，它们的值依次等于0，1，2等
+ 每一个被注册的帧缓冲区硬件设备在/dev/graphics目录下都有一个对应的设备文件fb<minor>
+ 最后会通过调用函数fb_notifier_call_chain来通知帧缓冲区控制台

## fbcon
+ 帧缓冲区控制台在内核中对应的驱动程序模块为fbcon - `kernel/goldfish/drivers/video/console/fbcon.c`
+ device_create来创建一个类别为graphics的设备fbcon
+ fb_register_client来监听帧缓冲区硬件设备的注册事件

## Gralloc
https://blog.csdn.net/luoshengyang/article/details/7747932 -- 2
Android帧缓冲区（Frame Buffer）硬件抽象层（HAL）模块Gralloc的实现原理分析
+ Gralloc模块(HAL) ->  gralloc设备(图形缓冲区) -> FB设备(Linux字符设备 帧缓冲区)
+ hw_get_module /system/lib/hw和/vendor/lib/hw load dlopen dlsym


## Butter
https://blog.csdn.net/innost/article/details/8272867
+ Android Project Butter分析 -- 鄧平凡

