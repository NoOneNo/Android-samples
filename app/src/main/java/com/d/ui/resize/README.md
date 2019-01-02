# resize with statueBar & NavBar


## ViewRootImpl#dispatchResized
+ frame 0,0 - 1080,1920
+ overscan 0,0 - 0,0
+ content 0,63 - 0,867
+ visible 0,63 - 0,867
+ stable 0,63 - 0,126

DecorView#onApplyWindowInsets
+ systemWindow 0,63 - 0,867  (content)
+ windowDecor 0,0 - 0,0
+ stable 0,63 - 0,126 (stable)

### View.SYSTEM_UI_FLAG_FULLSCREEN // hide statueBar
+ content 0,0 - 0,126
+ visible 0,0 - 0,126

keyBoard 显示的时候，statueBar弹出，resize生效
+ content 0,63 - 0,867
+ visible 0,63 - 0,867

### WindowManager.LayoutParams.FLAG_FULLSCREEN
+ content 0,0 - 0,126
+ visible 0,0 - 0,126

keyBoard 显示的时候，content无变化，statueBar不弹出，resize不生效， onApplyWindowInsets不会被调用
+ content 0,0 - 0,126
+ visible 0,0 - 0,867

### SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN ?

### view#fitsSystemWindows 

+ https://blog.csdn.net/smileiam/article/details/69055963
+ https://stackoverflow.com/questions/7417123/android-how-to-adjust-layout-in-full-screen-mode-when-softkeyboard-is-visible
