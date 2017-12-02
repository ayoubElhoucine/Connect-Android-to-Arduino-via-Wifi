package com.tofaha.Android_wifi.ui.main

/**
 * Created by ayoub on 11/30/17.
 */
interface MainView {
    fun openConnection()
    fun closeConnection()
    fun sendMessage(msg : String)
    fun receiveMessage()
}