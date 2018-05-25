2018-05-22

## Theme
+ ThemeOverlay.AppCompat 和 Theme.AppCompat 区别

## Style
https://developer.android.com/guide/topics/ui/look-and-feel/themes
https://developer.android.com/guide/topics/resources/style-resource
+ style 是一系列属性的集合 是针对单个View的，Theme 是有继承关系，并且可以应用到app或者activity

```
<resources>
    <style name="GreenText" parent="TextAppearance.AppCompat">
        <item name="android:textColor">#00FF00</item>
    </style>
</resources>
```
style:
TextAppearance.AppCompat
@android:style/TextAppearance

item:
android:textColor


```
<TextView
    style="@style/GreenText"
    ... />
```

+ 可以通过文档寻找Theme Style可以设置的属性
+ In XML: `@[package:]style/style_name`


## 组件
Window
Status Bar
Action Bar
app bar  https://developer.android.com/training/appbar/index.html
floating action button https://developer.android.com/guide/topics/ui/floating-action-button.html
Dialog


## Vector Drawable
support Lib 中有许多可以跟主题一同变化的图形图片是通过这种方式实现的.
但是是private的,所以无法直接使用,但是可以copy出来使用.
好像有版本限制(5.0 *TODO*),详细介绍 https://android-developers.googleblog.com/2016/02/android-support-library-232.html
https://developer.android.com/guide/topics/graphics/vector-drawable-resources
https://developer.android.com/studio/write/vector-asset-studio


## appCpmpat 依赖
```

+---com.android.support:support-v4:27.1.1
|    +--- com.android.support:support-compat:27.1.1 (*)
|    +--- com.android.support:support-media-compat:27.1.1
|    +--- com.android.support:support-core-utils:27.1.1 (*)
|    +--- com.android.support:support-core-ui:27.1.1 (*)
|    \--- com.android.support:support-fragment:27.1.1 (*)

+--- com.android.support:appcompat-v7:27.1.1
|    +--- com.android.support:support-annotations:27.1.1
|    +--- com.android.support:support-core-utils:27.1.1
|    +--- com.android.support:support-fragment:27.1.1
|    +--- com.android.support:support-vector-drawable:27.1.1
|    \--- com.android.support:animated-vector-drawable:27.1.1


\--- com.android.support:support-compat:27.1.1
     +--- com.android.support:support-annotations:27.1.1
     \--- android.arch.lifecycle:runtime:1.1.0
          +--- android.arch.lifecycle:common:1.1.0
          \--- android.arch.core:common:1.1.0

```

```
+--- com.android.support:cardview-v7:27.1.1
+--- com.android.support:preference-v7:27.1.1


+--- com.android.support:design:27.1.1
+--- com.android.support:customtabs:27.1.1
+--- com.android.support:animated-vector-drawable:27.1.1 (*)
+--- com.android.support.constraint:constraint-layout:1.1.0
```


