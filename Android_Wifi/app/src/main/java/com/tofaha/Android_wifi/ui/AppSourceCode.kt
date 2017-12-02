package com.tofaha.Android_wifi.ui

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.tofaha.Android_wifi.R
import kotlinx.android.synthetic.main.activity_app_source_code.*
import android.content.Intent
import android.net.Uri
import com.tofaha.Android_wifi.app.Constant


class AppSourceCode : AppCompatActivity() {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_source_code)

        window.statusBarColor = resources.getColor(R.color.blue_menu_button)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.blue_menu_button)))

        my_repo_link.setOnClickListener({
            val browser = Intent(Intent.ACTION_VIEW, Uri.parse(Constant.REPO_LINK))
            startActivity(browser)
        })

    }
}
