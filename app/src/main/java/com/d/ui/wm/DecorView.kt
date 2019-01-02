package com.d.ui.wm

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.gesture.GestureOverlayView
import android.os.Bundle
import android.support.v4.view.ViewConfigurationCompat
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Scroller

class BadBoy(val context: Context) {
    fun getView() {
        // 这样获得的　WindowManagerImpl　没有　window
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

//        val names = WindowManagerGlobal.getInstance().viewRootNames
//        Log.i("TAG" , "" + names)
//
//        val view = WindowManagerGlobal.getInstance().getRootView(names[0])
//        Log.i("TAG" , "" + view)

    }
}

@SuppressLint("Registered")
class DecorViewActivity : Activity() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // https://juejin.im/entry/571338c7c4c9710054cea455
        // ActivityThread#performLaunchActivity 调用Activity的attach()

        // Window mWindow = PhoneWindow

        // PhoneWindow setContentView 的实现类

        // WindowMangerService
        // mWindowManager = mWindow.getWindowManager();
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContentView

        // decorView
        window.decorView

        // findViewById 的实现: getDecorView().findViewById(id);
    }

    override fun onResume() {
        super.onResume()

        // WindowManager addView ( add DecoView to ViewRoot ViewRoot不是View)
        // ViewRoot = new ViewRootImpl
        // 就是说activity resume的时候ViewRoot和DecorView关联

        // ViewRoot 的 performTraversals 是一切绘制的开始
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        // View  的Measure过程 和Activity的创建不是同步的, 所以无法在onResume获得正确的View宽高

        // View 初始化好之后调用
        window.decorView.post {  }
    }
}

class CustomView(context: Context) : View(context) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // MeasureSpec Mode
        // EXACTLY 大小已经被父容器确定, MatchParent, 或者指定数值
        // AT_MOST 不能超过父元素, wrapContent, 对于自定义View需要自己实现这个!!

        // setMeasuredDimension()
        // 决定了测量宽高

        // 这里有一个最小宽度问题, 由view的minWith, 和View背景的minWith, 较大值决定 !! 为啥
        // 图片的最小宽度哪里来?

        //
        measuredWidth
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        // 决定四个顶点位置
        getTop()

        // 实际宽高
        width
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        ViewConfiguration.get(context).scaledTouchSlop

        val vt = VelocityTracker.obtain()
        vt.addMovement(event)

        vt.computeCurrentVelocity(1000)

        val gd = GestureDetector(context, GestureDetector.SimpleOnGestureListener())
        gd.onTouchEvent(event)

        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        super.computeScroll()

        val sc = Scroller(context)

        // scrollTo()

        // scrollBy() 基于当前位置滑动

        scrollX // view内容　和　view左边缘的距离

        // ObjectAnimator.ofFloat()
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
    }
}

class CustomViewGroup(context: Context) : ViewGroup(context) {

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
