package com.tofaha.Android_wifi.ui

import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.tofaha.Android_wifi.Pref
import com.tofaha.Android_wifi.R
import com.tofaha.Android_wifi.app.MyData
import com.tofaha.Android_wifi.app.TofahaApplication
import com.tofaha.Android_wifi.connection.CLoseConnection
import com.tofaha.Android_wifi.connection.OpenConnection
import com.tofaha.Android_wifi.connection.SendMessages
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.Socket
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private var openConnection : OpenConnection? = null
    private var closeConnection : CLoseConnection? = null
    private var sendMessages : SendMessages? = null

    @Inject
    lateinit var pref: Pref

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (this.application as TofahaApplication).getAppComponent()!!.inject(this)

        ButterKnife.bind(this)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue_menu)))
        window.statusBarColor = resources.getColor(R.color.blue_menu)

    }

    @OnClick(R.id.fab)
    operator fun next() {
        var dialogFrag = FloatingMenuFragment.newInstance()
        dialogFrag.setParentFab(fab)
        dialogFrag.show(getSupportFragmentManager(), dialogFrag.getTag())
        //Toast.makeText(this, "Enter valid IP address", Toast.LENGTH_SHORT).show()
    }

    @OnClick(R.id.send_message)
    fun sendMessage(){
        sendMessages = SendMessages("dfbjkdfb")
        sendMessages!!.execute()
    }



    override fun onResume() {
        super.onResume()
        openConnection = OpenConnection(pref.ipAddress!!, pref.portNumber)
        openConnection!!.execute()
    }

    override fun onPause() {
        super.onPause()
        closeConnection = CLoseConnection()
        closeConnection!!.execute()
    }

}
