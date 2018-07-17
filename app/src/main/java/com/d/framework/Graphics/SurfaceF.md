## SurfaceFlinger
https://blog.csdn.net/luoshengyang/article/details/7846923 -- 3
Android应用程序与SurfaceFlinger服务的关系概述和学习计划
+ Application -> SharedBufferStack(共享内存) -> SurfaceFlinger服务
+ 为什么Binder有内存限制

https://blog.csdn.net/luoshengyang/article/details/7857163
与SurfaceFlinger服务的连接过程
+ 智能指针
+ ISurfaceComposerClient、Client以及BpSurfaceComposerClient 是Binder的进程间接口

https://blog.csdn.net/luoshengyang/article/details/7867340
共享UI元数据（SharedClient）的创建过程
+ SurfaceFlinger服务就会从这个SharedClient对象中取出一个SharedBufferStack出来