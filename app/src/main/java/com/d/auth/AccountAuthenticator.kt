package com.d.auth

import android.accounts.AccountManager
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity

class SystemAccount {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getAccounts(context: Context) {
        val accounts = AccountManager.get(context).accounts

        val accMag = AccountManager.get(context)
        accMag.getAccountsByType("com.google")
        accMag.getAccountsByType("com.xiaomi")
        val intent = AccountManager.newChooseAccountIntent(null, null,
                arrayOf("com.google", "com.xiaomi"), null, null,
                null, null)

    }

    fun getAuthToken():String {
        return ""
    }

    fun requestPermissions(activity: AppCompatActivity) {
        ActivityCompat.requestPermissions(activity, arrayOf("android.permission.GET_ACCOUNTS"), 100)
    }
}