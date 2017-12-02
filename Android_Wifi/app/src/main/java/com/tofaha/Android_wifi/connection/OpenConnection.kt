package com.tofaha.Android_wifi.connection

import android.os.AsyncTask

import com.tofaha.Android_wifi.app.MyData

import java.io.IOException
import java.net.Socket

/**
 * Created by ayoub on 11/26/17.
 */

class OpenConnection(private val ipAddress: String, private val portNumber: Int) : AsyncTask<Void, String, Void>() {


    override fun doInBackground(vararg voids: Void): Void? {
        try {
            MyData.socket = Socket(ipAddress, portNumber)
            System.out.println("connection opened")

            if (MyData.THREAD_RUNNING == false){
                MyData.mainActivity.receiveMessage()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }
}
