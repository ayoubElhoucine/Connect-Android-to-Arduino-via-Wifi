package com.tofaha.Android_wifi.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast

import com.tofaha.Android_wifi.Pref
import com.tofaha.Android_wifi.R
import com.tofaha.Android_wifi.Util
import com.tofaha.Android_wifi.app.TofahaApplication

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.tofaha.Android_wifi.ui.main.MainActivity

class InfoActivity : AppCompatActivity() {

    @BindView(R.id.ip_address)
    lateinit var ipAddress: EditText

    @BindView(R.id.port_number)
    lateinit var portNumber: EditText

    @Inject
    lateinit var pref: Pref

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        (this.application as TofahaApplication).getAppComponent()!!.inject(this)

        ButterKnife.bind(this)

        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue_menu)))
        window.statusBarColor = resources.getColor(R.color.blue_menu)

        if (Util.isValidIp(pref!!.ipAddress!!) && pref!!.portNumber != 0) {
            startMainActivity()
        }
    }

    @OnClick(R.id.login_next)
    fun next() {

        if (Util.isValidIp(ipAddress!!.text.toString())) {
            pref!!.ipAddress = ipAddress!!.text.toString()
        } else {
            Toast.makeText(this, "Enter valid IP address", Toast.LENGTH_SHORT).show()
            return
        }

        if (portNumber!!.text.toString() != "") {
            pref!!.portNumber = Integer.parseInt(portNumber!!.text.toString())
        } else {
            Toast.makeText(this, "Enter port number", Toast.LENGTH_SHORT).show()
            return
        }

        startMainActivity()
    }

    fun startMainActivity() {
        val intent = Intent(this@InfoActivity, MainActivity::class.java)
        intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TOP
                or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
