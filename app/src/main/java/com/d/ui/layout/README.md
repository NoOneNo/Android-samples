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
+ a vertical LinearLayout, toolbar 只是作为其中一个子view
+ app:layout_scrollFlags. setScrollFlags(int)
+ a direct child within a CoordinatorLayout

## AppBarLayout layout_scrollFlags
https://medium.com/@tonia.tkachuk/appbarlayout-scroll-behavior-with-layout-scrollflags-2eec41b4366b
+ scroll : appbar 和滑动的内容是一体的, 不常用但是一个基础
+ enterAlways : 任何时候乡下滚动, appbar都会出现 
enterAlwaysCollapsed 
exitUntilCollapsed : AppBarLayout 会有一个最小高度 minHeight
snap : 不会有显示一半的情况

## CollapsingToolbarLayout
https://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events
+ 作为AppBarLayout 的child, toolbar的parent, 制造视差效果
+ layout_collapseMode pin 或 parallax