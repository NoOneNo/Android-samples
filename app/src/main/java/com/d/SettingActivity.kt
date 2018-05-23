package com.d

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceFragmentCompat
import com.d.uidraw.progress.EditTextFragment
import com.d.uidraw.progress.ProgressFragment
import com.d.webview.WebViewActivity

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(android.R.id.content, SettingFragment(), "tag").commit()
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


    }
}