package com.d.ui.layout

import android.support.v4.app.Fragment
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.text.Layout
import android.text.SpannableString
import android.text.style.LeadingMarginSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.d.R
import kotlinx.android.synthetic.main.text_layout.*


class TextFragment : Fragment() {
    private val mTempVisibleRect = Rect()

    val ht = HandlerThread("ht")
    var newView:TextView? = null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.text_layout, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ht.start();
        val handler = Handler(ht.looper)
        val mainhandler = Handler(Looper.getMainLooper())
        handler.post {
            newView = TextView(context)
            newView!!.setText("hhhhhh");

            mainhandler.post{
                group.addView(newView);
            }
        }


        apply_text.setOnClickListener {

            val line1text = SpannableString(edit_text.text)
            line1text.setSpan(object :LeadingMarginSpan{
                var layoutWidth = 80
                override fun drawLeadingMargin(c: Canvas?, p: Paint?, x: Int, dir: Int, top: Int, baseline: Int, bottom: Int, text: CharSequence?, start: Int, end: Int, first: Boolean, layout: Layout?) {
                    Log.e("debug", "layout width:"+ layout!!.width)
                    layoutWidth = layout.width
                }

                override fun getLeadingMargin(first: Boolean): Int {
                    Log.e("debug", "getLeadingMargin:")
                    if (first) {
                        return layoutWidth/2
                    } else {
                        return 20
                    }
                }

            }, 0, line1text.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

            line1.text = line1text
        }
    }
}
