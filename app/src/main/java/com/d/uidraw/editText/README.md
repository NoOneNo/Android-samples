2018-05-22

## Android UI绘制原理

## UI 绘制 与Android生命周期

## 从设计和交互的角度来看 Progress Bar

## Android 提供的　Progress Bar
https://developer.android.com/reference/android/widget/ProgressBar
+ android.widget.ProgressBar api overview
+ Determinate and indeterminate 是否有进度显示

https://material.io/design/components/progress-indicators.html#usage
+ material progress indicator
+ Linear and circular
+ Determinate and indeterminate

progressbar是一张图片，一个view, 继承自View，有View的一切属性。

不要使用progress Dialog，这样会阻塞用户输入，体验不好，可以将progress Bar放在UI中，或者通知中。

但是例如登录来说，在登录的过程中，需要隐藏输入框和按钮阻塞用户输入。
miui 是使用dialog的方式阻塞用户，google是使用下一步和progressBar的方式，隐藏输入框。


## github上的 Progress Bar


