https://blog.csdn.net/luoshengyang/article/details/6651971
Android系统匿名共享内存Ashmem（Anonymous Shared Memory）简要介绍和学习计划
+ 以驱动程序的形式实现在内核空间
+ 能够辅助内存管理系统来有效地管理不再使用的内存块
+ 通过Binder进程间通信机制来实现进程间的内存共享
+ MemoryFile native_open native_mmap
+ IMemoryService.stub IMemoryService.proxy IMemoryService.Stub.asInterface
+ ServiceManager.addService startService ServiceManager.getService

https://blog.csdn.net/luoshengyang/article/details/6664554
（Anonymous Shared Memory）驱动程序源代码分析
+ 实现非常小巧，总共代码不到700行
+ misc_register
+ file表示这个共享内存在临时文件系统tmpfs中对应的文件，在内核决定要把这块共享内存对应的物理页面回收时，就会把它的内容交换到这个临时文件中去
+ Linux内核的slab缓冲区
+ shmem_file_setup 在临时文件系统tmpfs中创建一个临时文件
+ Linux内核源代码情景分析 Unix进程间通信第七小节共享内存
+ Linux内核源代码情景分析 第二章内存管理第十三小节系统调用mmap

https://blog.csdn.net/luoshengyang/article/details/6666491
在进程间共享的原理分析
+ flat_binder_object BINDER_TYPE_FD fget task_get_unused_fd_flags task_fd_install
+ 打开文件结构struct file是可以在进程间共享的，它与文件描述符不一样

https://blog.csdn.net/cywosp/article/details/38965239
Linux中的文件描述符与打开文件之间的关系
+ 所有执行I/O操作的系统调用都通过文件描述符
+ 程序刚刚启动的时候，0是标准输入，1是标准输出，2是标准错误 （写执行脚本文件的时候会用到）
+ POSIX标准要求每次打开文件时（含socket）必须使用当前进程中最小可用的文件描述符号码，因此，在网络通信过程中稍不注意就有可能造成串话
+ 单个进程最大打开文件数做默认值处理（称之为用户级限制），默认值一般是1024，使用ulimit -n命令可以查看
+ 进程级的文件描述符表 用户空间， 包含一个指针指向内核空间
+ 系统级的文件描述符表
+ 文件系统的i-node表
