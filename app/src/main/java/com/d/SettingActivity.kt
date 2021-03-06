package com.d

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import com.d.ui.layout.TextFragment
import com.d.ui.progress.EditTextFragment
import com.d.ui.progress.ProgressFragment
import com.d.ui.resize.ResizeFragment
import com.d.ui.wm.BadBoy
import com.d.webview.WebViewActivity

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(android.R.id.content, SettingFragment(), "tag").commit()

        val wl = window.attributes;
        wl.alpha = 0.5f;
        window.attributes = wl;
    }
}

class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_pref)

        findPreference("web_view").setOnPreferenceClickListener {
            startActivity(Intent(context, WebViewActivity::class.java))
            return@setOnPreferenceClickListener true
        }


        findPreference("progress").setOnPreferenceClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(android.R.id.content, ProgressFragment()).addToBackStack(null).commit()
            return@setOnPreferenceClickListener true
        }

        findPreference("edit_text").setOnPreferenceClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(android.R.id.content, EditTextFragment()).addToBackStack(null).commit()
            return@setOnPreferenceClickListener true
        }

        findPreference("get_view").setOnPreferenceClickListener {
            BadBoy(activity!!.applicationContext).getView()
            return@setOnPreferenceClickListener true
        }


        findPreference("resize").setOnPreferenceClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(android.R.id.content, ResizeFragment()).addToBackStack(null).commit()
            return@setOnPreferenceClickListener true
        }

        findPreference("text").setOnPreferenceClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(android.R.id.content, TextFragment()).addToBackStack(null).commit()
            return@setOnPreferenceClickListener true
        }

    }
}
