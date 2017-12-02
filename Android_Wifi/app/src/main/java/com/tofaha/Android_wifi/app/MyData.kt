package com.tofaha.Android_wifi.app

import com.tofaha.Android_wifi.ui.main.MainActivity
import java.net.Socket

/**
 * Created by ayoub on 11/26/17.
 */

object MyData {
    lateinit var socket: Socket
    lateinit var mainActivity : MainActivity
    var THREAD_RUNNING = false
}
