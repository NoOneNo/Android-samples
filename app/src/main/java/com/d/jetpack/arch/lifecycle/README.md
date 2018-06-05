2018-05-22

https://developer.android.com/topic/libraries/architecture/lifecycle

+ Lifecycle
+ LifecycleObserver
+ LifecycleOwner
+ ProcessLifecycleOwner
+ LifecycleRegistry

+ Fragments and Activities in Support Library 26.1.0 and later already implement the LifecycleOwner interface.
+ lifecycle.getCurrentState().isAtLeast(STARTED) -- query the current state.
+ if the callback runs a fragment transaction after the activity state is saved, it would trigger a crash


+ Lifecyle 对于  onSaveInstanceState() 和 onStop() 的处理有些时序问题, 详情见原文