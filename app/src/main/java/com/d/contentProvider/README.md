## Content providers
https://developer.android.com/guide/topics/providers/content-providers

+ 安全的暴露自己的app数据给其他应用
+ 作为一个抽象的数据接口使用，就是的存储实现可以所以随意切换，不影响上层逻辑
+ 可以对访问对象进行权限管理
+ 配合 client 端封装了跨进程和安全访问

所有存储操作，IO操作，要注意
+ 线程问题
+ 资源释放问题
+ 权限问题

## user dictionary
系统提供的一个contentProvider, 存储用户使用的单词频次
+ 是否有动态权限
+ 在删改查的权限是否分离
+ 是否有隐私问题，如何清除

## 使用
+ query 方法不会抛出异常, 返回cursor为null, Exception?
+ 需要防止用户输入恶意数据, 导致 SQL injection
+ 

## 提供
+ 确定自己真的需要ContentProvider吗
+ 选择存储 SQLite LevelDB RoomDatabase Room(android提供的架构组件)
+ 你甚至可以提供基于网络的数据
+ 微信朋友圈保存朋友圈的数据就是 BLOB 类型。


http://gityuan.com/2016/07/30/content-provider/
## 理解ContentProvider原理
+ 并没有Activity那样复杂的生命周期，只有简单地onCreate过程。
+ 是吗是Android的deep sleep, SystemClock.uptimeMillis();