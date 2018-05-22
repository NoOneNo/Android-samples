2018-05-22

## Style
https://developer.android.com/guide/topics/ui/look-and-feel/themes
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

item
android:textColor


```
<TextView
    style="@style/GreenText"
    ... />
```



## 组件
Window
Status Bar
Action Bar
Dialog