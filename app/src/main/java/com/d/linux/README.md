内存映射
https://blog.csdn.net/mg0832058/article/details/5890688
+ maps files or devices into memory
+ 系统调用mmap
+ 进程无需再调用read或write对文件进行读写
+ 必须通过MMU将逻辑地址转换成物理地址, 找不到缺页中断, 硬盘awap找, 找不到,通过mmap映射关系找
+ read系统调用, 先将文件拷贝到内核空间缓冲区, 在拷贝到用户空间

内核空间缓冲区
https://blog.csdn.net/dlutbrucezhang/article/details/9050467
+ 三层 系统调用接口 (GNU C Library) (glibc), 独立于体系结构的内核代码,  BSP（Board Support Package）
+ 7个组件, SCI, PM, VFS, MM, NS, Arch, DD
+ 内核缓冲区防止频繁的操作磁盘, 优化写操作, 提高效率. 但是导致写入不及时
+ 内核空间缓冲区 不在堆 也不在栈

共享内存
+ Share memory

Linux环境进程间通信
https://www.ibm.com/developerworks/cn/linux/l-ipc/part1/index.html