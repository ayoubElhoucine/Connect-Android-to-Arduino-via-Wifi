package com.tofaha.esp8266.ui

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.widget.Toast
import butterknife.ButterKnife
import butterknife.OnClick
import com.tofaha.esp8266.R
import com.tofaha.esp8266.Util
import com.tofaha.esp8266.app.TofahaApplication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


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
        Toast.makeText(this , "send" , Toast.LENGTH_SHORT).show()
    }

}
