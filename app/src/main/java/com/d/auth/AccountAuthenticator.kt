package com.d.auth

import android.accounts.Account
import android.support.v4.app.ActivityCompat

class SystemAccount {
    fun getAccount():Account {
        return Account("name", "type")
    }

    fun getAuthToken():String {
        return ""
    }

    fun getAccountPermission() {
//        ActivityCompat.PermissionCompatDelegate()
    }
}