
## 管道，内核是怎么实现管道
标准输入输出，文件描述符，fork系统调用，read write系统调用，pipe系统调用，shell命令，dup2系统调用，
虚拟文件系统pipefs

dup：修改fd指向的文件结构体
read：是否阻塞由设备属性决定
pipefs文件系统：cat /proc/filesystems 查看支持的文件系统
file->f_op->read
是如何陷入中断的？

## 命名管道FIFO 
mknod和mkfifo， read_fifo_fops，read_pipe_fops

## XSI  IPC
ftok，key_t， 
