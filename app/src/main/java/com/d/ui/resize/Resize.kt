package com.d.ui.resize

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.AppCompatEditText
import android.util.DisplayMetrics
import android.view.*
import com.d.R
import kotlinx.android.synthetic.main.resize_layout.*
import com.google.common.io.Flushables.flush
import android.graphics.Bitmap
import android.os.Environment.getExternalStorageDirectory
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ResizeFragment : Fragment() {
    private val mTempVisibleRect = Rect()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.resize_layout, null)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val decorView = activity!!.window.decorView

        // ViewCompat#setOnApplyWindowInsetsListener
//        decorView!!.setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {
//            override fun onApplyWindowInsets(v: View, insets: WindowInsets?): WindowInsets {
//                val r = v.onApplyWindowInsets(insets)
//                Log.d("", "")
//                return r
//            }
//        })
//
//        (activity!!.window.decorView as ViewGroup).getChildAt(0).setOnApplyWindowInsetsListener(object : View.OnApplyWindowInsetsListener {
//            override fun onApplyWindowInsets(v: View, insets: WindowInsets?): WindowInsets {
//                val r = v.onApplyWindowInsets(insets)
//                Log.d("", "")
//                return r
//            }
//        })


        decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            @TargetApi(Build.VERSION_CODES.M)
            override fun onGlobalLayout() {



                val contentRootVisRect = Rect()

                val display = activity!!.window.windowManager.defaultDisplay
                val contentRoot = (decorView as ViewGroup).getChildAt(0)


                val displayRealMetrics = DisplayMetrics()
                display.getRealMetrics(displayRealMetrics)

                val displayRectSize = Rect()
                display.getRectSize(displayRectSize)

//                val rootWindowInsets = decorView.rootWindowInsets

                val contentRootHeight = contentRoot.height

                val visibleRect = Rect()
                decorView.getWindowVisibleDisplayFrame(visibleRect)

                val visibleHeight = visibleRect.bottom - visibleRect.top
                val keyboardHeight0 = contentRootHeight - visibleHeight

                val keyboardHeight1 = contentRootHeight - visibleRect.bottom

                val displayMetrics = context!!.resources.displayMetrics
                val keyboardHeight2 = displayMetrics.heightPixels - visibleRect.bottom

                contentRoot.getGlobalVisibleRect(contentRootVisRect)
                val keyboardHeight3 = contentRootVisRect.bottom - visibleRect.bottom

                val contentRootLocation = IntArray(2)
                contentRoot.getLocationOnScreen(contentRootLocation) // contentRoot getLocationInWindow
                val keyboardHeight4 = contentRootLocation[1] + contentRootHeight - visibleRect.bottom

                // visibleRect previous - now

                val keyboardHeight = contentRoot.paddingBottom
                contentRoot.setPadding(contentRoot.paddingLeft, contentRoot.paddingTop, contentRoot.paddingRight, contentRoot.paddingBottom)

            }
        })

        adjustResize.setOnClickListener {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        adjustPan.setOnClickListener {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        }

//        activity!!.window.decorView.fitsSystemWindows
//        decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN // WindowManager.LayoutParams#FLAG_FULLSCREEN
//        decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_FULLSCREEN.inv()
//
//        decorView.systemUiVisibility = decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        dialog_show.setOnClickListener {
            showDialog(context!!, "hello")
        }

//        full_screen.setOnClickListener {
//            val flag = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//            if (decorView.systemUiVisibility and flag == 0) {
//                decorView.systemUiVisibility = decorView.systemUiVisibility or flag
//            } else {
//                decorView.systemUiVisibility = decorView.systemUiVisibility and flag.inv()
//            }
//        }

        full_screen.setOnClickListener {
            val flag = WindowManager.LayoutParams.FLAG_FULLSCREEN
            if (activity!!.window.attributes.flags and flag == 0) {
                activity!!.window.addFlags(flag)
            } else {
                activity!!.window.clearFlags(flag)
            }
        }

        full_screen_nav.setOnClickListener {
            val flag =  WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            if (activity!!.window.attributes.flags and flag == 0) {
                activity!!.window.addFlags(flag)
            } else {
                activity!!.window.clearFlags(flag)
            }
        }

        hide_nav_bar.setOnClickListener {
//            val flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//            if (decorView.systemUiVisibility and flag == 0) {
//                decorView.systemUiVisibility = decorView.systemUiVisibility or flag
//            } else {
//                decorView.systemUiVisibility = decorView.systemUiVisibility and flag.inv()
//            }

            fullscreenAndHideNavigation(decorView)
        }

        screen_shot.setOnClickListener{
            takeScreenshot(decorView)
        }
    }

    private fun takeScreenshot(view: View) {
        val now = Date()
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"

            // create bitmap screen capture
            val v1 = view
            v1.setDrawingCacheEnabled(true)
            val bitmap = Bitmap.createBitmap(v1.getDrawingCache())
            v1.setDrawingCacheEnabled(false)

            val imageFile = File(mPath)

//            val outputStream = FileOutputStream(imageFile)
//            val quality = 100
//            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
//            outputStream.flush()
//            outputStream.close()

//            openScreenshot(imageFile)


            val imageView = ImageView(activity)
            imageView.setImageBitmap(bitmap)

            val dialog = AlertDialog.Builder(context).setView(imageView).setOnDismissListener {

            }.create()
            dialog.show()


        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }
    }

    private fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        startActivity(intent)
    }

    private fun showDialog(context: Context, msg: String) {
        val dialog = AlertDialog.Builder(context).setView(AppCompatEditText(context)).setOnDismissListener {

        }.create()
        dialog.show()
        dialog.window.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog.window.attributes.height = 400
        dialog.window.attributes.gravity = Gravity.BOTTOM
        dialog.window.attributes = dialog.window.attributes

        val decorView = dialog.window.decorView
        decorView.setPadding(0, 0, 0, 0)
        decorView.setBackgroundColor(resources.getColor(R.color.black))

        decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(mTempVisibleRect)
                decorView.rootWindowInsets
                decorView.height
            }
        })
    }

    private fun fullscreenAndHideNavigation(decorView: View) {
        decorView.setSystemUiVisibility(
             View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }
}
