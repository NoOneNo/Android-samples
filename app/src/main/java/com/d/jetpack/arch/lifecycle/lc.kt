package com.d.jetpack.arch.lifecycle

import android.arch.lifecycle.*
import android.support.v4.app.Fragment

class MyObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connect() {

    }


}

class MyLifecycleOwner : LifecycleOwner {

    override fun getLifecycle(): Lifecycle {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

// LifecycleObserver
// LifecycleOwner
// Lifecycle
// LifecycleRegistry
// Lifecycle.Event
// Lifecycle.State


val liveString = MutableLiveData<String>()

val stringObserver = Observer<String> {

}

fun doit() {
    liveString.observe(MyLifecycleOwner(), stringObserver)
}

// LiveData
// Observer 可以被包装成 LifecycleBoundObserver

// UI controller 如何理解

// java中的数据结构, 集合, Collections, Collection 透过这两个类去理解, 存储结构, 线程安全

// 启示1: 我们有时会监听view的变化, 但是我们真的是监听view吗, 还是View绑定的Model更重要, 基于数据的响应式编程可以更好的做到UI和数据和逻辑的分离
// 启示2: 响应式可以分为 对UI, 事件, 数据, 状态装换的响应, 响应的结果可以分为逻辑, UI, 数据的修改
// 启示3: 响应式是当..., 函数式是...之后..., 函数式这么说又有点像命令式编程, 响应式有点声明式的感觉
// 启示4: 我们需要监听生命周期的原因是, 有些对象的生命默认比activity更长, 我们需要手动进行销毁, 这个销毁应该是自觉的, 还是被动的


class MyViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
    }
}

fun testVM() {
    val vm = ViewModelProviders.of(Fragment()).get(MyViewModel::class.java)
}

// 疑问1. VM是怎么区分是Activity的旋转导致的onDestroy还是finish导致的onDestroy?




// ANdroid 内存分配, native内存限制
// http://blog.qiji.tech/archives/12312

// MVC MVP MVVM
// https://www.tianmaying.com/tutorial/AndroidMVC
// 区别1: MVC中Controller是activity, MVP中View是Activity
// 区别2: MVC中VC没有绑定关系, 多个V可以用一个C, C可以切换V, MVP中V和P是一对
// 关键1: MVC和MVP中的 M 可以不依赖其他部分
// 关键2: MVVM应该是响应式的