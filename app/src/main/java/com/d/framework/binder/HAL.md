https://blog.csdn.net/luoshengyang/article/details/6567257
Android硬件抽象层（HAL）
+ Linux内核源代码版权遵循GNU License
+ Android源代码版权遵循Apache License
+ 把对硬件的支持分成了两层，一层放在用户空间（User Space），一层放在内核空间（Kernel Space）
+ 硬件抽象层运行在用户空间，而Linux内核驱动程序运行在内核空间

https://blog.csdn.net/luoshengyang/article/details/6568411
在Ubuntu上为Android系统编写Linux内核驱动程序
+ cdev 表示字符设备
+ 设备寄存器 不是 CPU寄存器
+ container_of宏 得到结构体的首地址
+ VFS inode（内存中的inode） 和 Ext2 inode（文件系统中的inode）-- http://www.ruanyifeng.com/blog/2011/12/inode.html
+ create_proc_entry proc文件系统
+ 模块加载和卸载 cdev_init cdev_add init_MUTEX memset

https://blog.csdn.net/sty23122555/article/details/51638697
+ Linux内核通信之---proc文件系统（详解）
+ sysfs 是一个与 /proc 类似的文件系统， 还有一个 debugfs 文件系统

https://blog.csdn.net/zqixiao_09/article/details/50864014

http://pwn4.fun/2017/03/05/Linux%E8%AE%BE%E5%A4%87%E9%A9%B1%E5%8A%A8%EF%BC%88%E4%B8%89%EF%BC%89%E6%96%87%E4%BB%B6%E7%B3%BB%E7%BB%9F/
+ 查看/proc/devices文件可以获知系统中注册的设备
+ 查看/dev目录可以获知系统中包含的设备文件

inode结构体在内核还是用户空间
https://blog.csdn.net/lanmanck/article/details/4366020
重要
+ file指针在用户空间
+ 同一个文件可以被打开好多次,所以可以对应很多struct file,但是只对应一个struct inode


## semaphore
https://www.cnblogs.com/nullzx/p/5270233.html
+ java C 中都有 semaphore
+ 两种操作： acquire（获取） 和 release（释放）
+ 一般常用非公平的信号量
+ AbstractQueuedSynchronizer 实现

https://blog.csdn.net/hyman_c/article/details/53727177
+ semaphore可以跨进程使用，而mutex只能在一个进程中使用
+ fopen memset fputs fseek fgets fwrite fclose
+ sem_wait sem_post sem_init sem_destroy
+ open mmap fork munmap close
+ 网络编程
+ PV 就是信号量操作

https://blog.csdn.net/koudaidai/article/details/8014782
fork出的子进程和父进程
+ 该函数被调用一次，但返回两次。两次返回的区别是子进程的返回值是0
+ 这2个进程共享代码空间，但是数据空间是互相独立的，子进程数据空间中的内容是父进程的完整拷贝

什么是中断

https://www.cnblogs.com/hoys/archive/2012/08/19/2646377.html
+ 在软件层次上是对中断机制的一种模拟
+ 信号是进程间通信机制中唯一的异步通信机制
