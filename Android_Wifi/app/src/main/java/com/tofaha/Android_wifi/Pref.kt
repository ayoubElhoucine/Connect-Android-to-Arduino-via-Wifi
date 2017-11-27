package com.tofaha.Android_wifi

import android.content.Context
import android.content.SharedPreferences

import com.tofaha.Android_wifi.app.Constant


/**
 * Created by ayoub on 11/23/17.
 */

class Pref(private val context: Context) {
    private val preferences: SharedPreferences

    var ipAddress: String?
        get() = this.preferences.getString(Constant.IP_ADDRESS, "non")
        set(ipAddress) = this.preferences.edit().putString(Constant.IP_ADDRESS, ipAddress).apply()

    var portNumber: Int
        get() = this.preferences.getInt(Constant.PORT_NUMBER, 0)
        set(portNumber) = this.preferences.edit().putInt(Constant.PORT_NUMBER, portNumber).apply()

    init {
        this.preferences = context.getSharedPreferences(context.packageName, 0)
    }
}
