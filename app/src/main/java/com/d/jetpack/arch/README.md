2018-05-22

App Architecture
https://developer.android.com/jetpack/docs/guide
+ you should not store any app data or state in your app components 

separation of concerns
https://en.wikipedia.org/wiki/Separation_of_concerns ( *TODO* )
+ Any code that does not handle a UI or operating system interaction should not be in these classes. (Activity, Fragment) (to avoid many lifecycle related problems)
+ you don't own those classes, they are just glue classes that embody the contract between the OS and your app (Activity, Fragment)

drive your UI from a model
+ your users won't lose data if the OS destroys your app, network connection is flaky or not connected
+ Models are components that are responsible for handling the data for the app

Recommended app architecture
+ If you already have a good way of writing Android apps, you don't need to change.

Building the user interface (ViewModel, LiveData)
+ 一个UI component(fragment Activity) 对应一个ViewModel用来保存数据,可以在onActivityCreated初始化
+ 如果你已经使用了 reactive streams library(RxJava,Agera), 可以继续使用, 但是要确保LifecycleOwner暂停摧毁的时候, data Stream 也暂停摧毁了
+ 或者android.arch.lifecycle:reactivestreams artifact to use LiveData with another reactive streams library 也可以处理内存泄露
+  LiveData is that it is lifecycle aware and will automatically clean up references when they are no longer needed.

Fetching data
+ REST API + Retrofit

Caching data
+ if the user rotates the screen or leaves and returns to the app, the existing UI will be visible instantly 

Persisting data (ROOM)
+ if the user leaves the app and comes back hours later, after the Android OS has killed the process
+ minimal boilerplate code.
+ broken SQL queries result in compile time errors instead of runtime failures
+ abstracts away some of the underlying implementation
+ allows observing changes to the database data
+ defines thread constraints 
+ 如果使用了其他ORM方案这个也不需要了




--------------

补充内存泄露和垃圾回收和循环引用
https://blog.csdn.net/lynn_Kun/article/details/73468705 -- 深入理解java虚拟机
+ 引用计数算法, 无法解决循环引用的问题
+ 可达性分析算法（主流算法）, 可以解决循环引用问题


其他
+ https://github.com/Juude/Awesome-Android-Architecture