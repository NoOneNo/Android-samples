## ConstraintLayout
+ ViewGroup
+ 更灵活的RelativeLayout , 更好的支持UI编辑器

## xmlns:app

## NestedScrollView
+ FrameLayout NestedScrollingParent, NestedScrollingChild2, ScrollingView
+ https://www.androiddesignpatterns.com/2018/01/experimenting-with-nested-scrolling.html
+ 嵌套的scroll, 和scrollView一样继承自FrameLayout


## CoordinatorLayout
+ ViewGroup NestedScrollingParent2
+ As a top-level application decor or chrome layout
+ As a container for a specific interaction with one or more child views
+ Behavior
+ Snackbar

## DecorView
+ DecorView 是一个 FrameLayout 
+ 初始化的时候 包含 action bar 和 content (FrameLayout)
+ setContentView 是 addView 到 content
+ hide state bar : https://developer.android.com/training/system-ui/status
+ AppCompatActivity 在这个基础上加了个 subDecor
+ ActionBar 有两种 WindowDecorActionBar 和 ToolbarActionBar 不能同时出现

## AppBarLayout 
+ a vertical LinearLayout
+ app:layout_scrollFlags. setScrollFlags(int)
+ a direct child within a CoordinatorLayout