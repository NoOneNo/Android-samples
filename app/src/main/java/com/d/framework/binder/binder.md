http://www.cnblogs.com/innost/archive/2011/01/09/1931456.html
Android深入浅出之Binder机制 -- 2011
+ TLS 局部线程存储
+ CORBA
+ IOCTL
+ 多进程/多线程文件操作

https://blog.csdn.net/universus/article/details/6211589
Android Bander设计与实现 - 设计篇 -- 2011
+ socket作为一款通用接口，其传输效率低，开销大
+ 可靠的身份标记只有由IPC机制本身在内核中添加
+ 它工作于内核态，提供open()，mmap()，poll()，ioctl()等标准文件操作，
+ 以字符驱动设备中的misc设备注册在设备目录/dev下，用户通过/dev/binder访问该它
+ mmap()分配的内存除了映射进了接收方进程里，还映射进了内核空间

指针超出进程空间可以读数据吗？
进程空间有多少超出怎么办？
mmap分配的空间位置，权限

http://www.cnblogs.com/geneil/archive/2011/12/03/2272869.html
linux设备驱动程序之简单字符设备驱动
+ 从内核空间拷贝到用户空间

https://blog.csdn.net/arethe/article/details/6941112
利用mmap实现用户空间与内核空间的共享内存通信
+ 设备文件的mmap由设备驱动自己实现
+ 内核空间 和 用户空间 共享同一段物理内存， 无需拷贝

多个线程或进程 如何读写 同一个 文件 或者设备文件

https://www.zhihu.com/question/48161206
+ MAP_SHARED 多个进程可以共享该映射，确切地说是共享改内存区域对应的物理页page frame，因为不同进程有不同的地址空间

http://www.cnblogs.com/albert1017/p/3849585.html
（原创）Android Binder设计与实现 - 实现篇（1）- 2014
+ 图解Binder

https://mr-cao.gitbooks.io/android/content/android-binder.html
BBinder BpBinder