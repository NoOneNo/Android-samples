package com.d

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.d.webview.KtorService

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, KtorService::class.java))

        startActivity(Intent(this, SettingActivity::class.java))
        finish()
    }
}
