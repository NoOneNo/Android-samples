package com.d.uidraw.progress

import android.os.Bundle
import android.support.annotation.StyleRes
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.d.R
import kotlinx.android.synthetic.main.edittext_layout.*

class EditTextFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.edittext_layout, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog_btn.setOnClickListener {
            showDialog(android.R.style.Theme_Dialog, "Theme_Dialog")
        }
        dialog_btn_holo.setOnClickListener {
            showDialog(android.R.style.Theme_Holo_Dialog, "Theme_Holo_Dialog")
        }
        dialog_btn_material.setOnClickListener {
            showDialog(android.R.style.Theme_Material_Dialog_Alert, "Theme_Material_Dialog_Alert")
        }

        dialog_device_default.setOnClickListener {
            showDialog(android.R.style.Theme_DeviceDefault_Dialog_Alert, "Theme_DeviceDefault_Dialog_Alert")
        }

        dialog_btn_appcompat.setOnClickListener {
            showDialog(R.style.Theme_AppCompat_Dialog_Alert, "Theme_AppCompat_Dialog_Alert")
        }
    }

    private fun showDialog(@StyleRes themeResId:Int, msg:String) {
        AlertDialog.Builder(activity!!, themeResId)
                .setTitle("Title")
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Positive", {_,_ ->})
                .setNegativeButton("Negative", {_,_ ->})
                .setNeutralButton("Neutral", {_,_ ->})
                .create().show()
    }
}