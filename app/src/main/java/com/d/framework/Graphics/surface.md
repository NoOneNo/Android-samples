## 创建Surface

### Layer, SharedBufferServer
SurfaceFlinger服务中，每一个绘图表面，即一个Layer对象，都关联有一个UI元数据缓冲区堆栈

Layer
成员SharedBufferServer UI元数据缓冲区堆栈
成员SurfaceLayer 继承LayerBaseClient::Surface

### Layer::SurfaceLayer
Layer::SurfaceLayer对象是一个Binder本地对象，它是SurfaceFlinger服务用来与Android应用程序建立通信的
Layer::SurfaceLayer类实现了ISurface接口

###　ISurface
requestBuffer 请求一个GraphicBuffer图形缓冲区

### SurfaceLayer的代理对象 BpSurface 和 SurfaceControl
SurfaceControl 
成员SurfaceComposerClient为单例对象, 
SurfaceComposerClient和SurfaceFlinger服务建立连接, 获得Client的Binder代理对象
成员BpSurface是SurfaceLayer代理对象
成员Surface对象用于绘制界面

Surface
成员BpSurface是SurfaceLayer代理对象
成员SurfaceClient为单例对象, 通过它来请求SurfaceFlinger服务创建共享UI元数据, 通过它来请求SurfaceFlinger服务渲染一个绘图表面
成员SharedBufferClient 应用程序这一侧描述一个UI元数据缓冲区堆栈
Android应用程序中，每一个绘图表面，即一个Surface对象，都关联有一个UI元数据缓冲区堆栈。

Surface类继承了EGLNativeBase类，而EGLNativeBase类又继承了ANativeWindow类
Android系统为OpenGL库提供的本地窗口使用ANativeWindow类来描述

## 什么叫每一个绘图表面

## SharedBufferServer类和SharedBufferClient
被Layer对象和一个Surface对象分别持有

基类SharedBufferBase
成员SharedClient, 指向一块UI元数据缓冲区
成员SharedBufferStack, 指向一个UI元数据堆栈, 指向SharedClient中一个元素
成员mIdentity, 描述一个绘图表面的ID

SharedClient
成员SharedBufferStack数组surfaces

SharedBufferClient
成员变量mNumBuffers、tail和queue_head
成员函数setDirtyRegion、setCrop和setTransform来设置对应的Surface的裁剪区域、纹理坐标以及旋转方向

SharedBufferServer
成员变量mBufferList指向了一个BufferList对象, 描述堆栈中缓冲区的空闲状态


==============

开机动画程序bootanim如何请求SurfaceFlinger创建Surface显示开机动画

1. 客户端获得Surface
获得SurfaceComposerClient(单例), 通过BpSurfaceComposerClient代理对象, 请求对应的SF内的Client, 
createSurface, 获得代理BpSurface, 对应SF中的SurfaceLayer
封装结果为SurfaceControl对象

2. SurfaceFlinger创建Surface
Binder本地对象Client, 通过mFlinger获得 SurfaceFlinger, 调用createSurface
调用createNormalSurface, 创建Layer, 添加Layer到一个列表
Layer.getSurface获得Layer::SurfaceLayer

3. Layer SurfaceLayer 初始化
成员函数graphicPlane 用来获得第N个显示屏
描述系统第1个显示屏的DisplayHardware对象hw
安全问题: ISurfaceComposer::eSecure 如何保证UI元数据不被进程拷贝
mNeedsBlending, mNeedsDithering 标示渲染时的 混合操作好和抖动操作
最后Layer创建SurfaceLayer对象, 并且互相持有引用
SurfaceLayer初始化很简单

4. SurfaceFlinger 管理 Layer/SurfaceLayer
Client对象描述一个正在请求SurfaceFlinger的Android进程
保存Layer到Client, 生成一个key来保存,简单.
保存Layer到SF, State对象有个LayerVector, 将Layer按照Z轴排列, 根据这个来计算可见性.

5. LayerBaseClient getSurface
弱指针升级为强指针

6. 客户端初始化Surface
成员GraphicBufferMapper对象, 用来将分配到的图形缓冲区映射到进程地址空间
成员SurfaceClient单例, 初始化的时候请求SF创建共享UI元数据
SharedBufferClient对象, 描述UI元数据堆栈
BpSurface的Binder代理对象
mIdentity、mFormat、mFlags、mWidth和mHeight分别用来描述一个Surface的ID、像素格式、用途、宽度和高度。

7. SurfaceClient.getTokenForSurface
BpSurfaceComposerClient 调用到 UserClient, 找到SF, 找到Layer

UserClient
成员变量mBitmap是一个int32_t值, 第n位等于1，那么就表示值等于n的Token已经被分配出去
找到一个0位分配给Layer
成员ctrlblk SharedClient对象是用来描述一组UI元数据缓冲区堆栈的

Layer
成员BufferManager getDefaultBufferCount就可以获得一个UI元数据缓冲区堆栈的大小












































