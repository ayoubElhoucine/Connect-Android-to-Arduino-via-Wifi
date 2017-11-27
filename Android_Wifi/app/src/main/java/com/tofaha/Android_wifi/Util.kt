package com.tofaha.Android_wifi

/**
 * Created by ayoub on 11/23/17.
 */

object Util {
    private val ip4Regex = "^(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})(\\.(25[0-5]|2[0-4][0-9]|1[0-9][0-9]|[0-9]{1,2})){3}$"

    fun isValidIp(ip: String): Boolean {
        return ip.matches(ip4Regex.toRegex())
    }
}
