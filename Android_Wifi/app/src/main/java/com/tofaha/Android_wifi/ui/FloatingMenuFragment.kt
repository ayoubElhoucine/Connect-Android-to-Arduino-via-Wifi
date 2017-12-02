package com.tofaha.Android_wifi.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment
import com.tofaha.Android_wifi.Pref
import com.tofaha.Android_wifi.R
import com.tofaha.Android_wifi.app.TofahaApplication

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.tofaha.Android_wifi.Util
import kotlinx.android.synthetic.main.ip_address_dialog.view.*
import kotlinx.android.synthetic.main.port_number_dialog.view.*

/**
 * Created by ayoub on 11/24/17.
 */

class FloatingMenuFragment : AAH_FabulousFragment() {

    @BindView(R.id.content_menu)
    lateinit var contentMenu: View

    @BindView(R.id.close_menu)
    lateinit var closeMenu: View

    @BindView(R.id.menu_ip_address_text)
    lateinit var ipAddress: TextView

    @BindView(R.id.menu_port_number_text)
    lateinit var portNumber: TextView

    @Inject
    lateinit var pref: Pref

    override fun setupDialog(dialog: Dialog, style: Int) {
        val myView = View.inflate(context, R.layout.float_menu, null)
        (activity.application as TofahaApplication).getAppComponent()!!.inject(this)
        ButterKnife.bind(this, myView)

        updateInfo()

        setAnimationDuration(400) //optional; default 500ms
        setPeekHeight(400) // optional; default 400dp
        //setCallbacks((Callbacks) getActivity()); //optional; to get back result
        //setAnimationListener((AnimationListener) getActivity()); //optional; to get animation callbacks
        setViewgroupStatic(closeMenu) // optional; layout to stick at bottom on slide
        //setViewPager(vp_types); //optional; if you use viewpager that has scrollview
        setViewMain(contentMenu) //necessary; main bottomsheet view
        setMainContentView(myView) // necessary; call at end before super
        super.setupDialog(dialog, style) //call super at last
    }

    @OnClick(R.id.close_menu)
    fun close() {
        closeFilter(this)
    }

    @OnClick(R.id.menu_ip_address)
    fun ipAddress() {
        showIpDialog()
    }

    @OnClick(R.id.menu_port_number)
    fun portNumber() {
        showPortDialog()
    }

    @OnClick(R.id.menu_share)
    fun shareApp() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.tofaha.Android_wifi")
        startActivity(Intent.createChooser(sharingIntent, "Share with"))
    }

    @OnClick(R.id.menu_code_source)
    fun appCodeSource() {
        startActivity(Intent(context , AppSourceCode::class.java))
    }

    private fun showIpDialog() {
        val alertDialog = AlertDialog.Builder(context).create()
        val myView = layoutInflater.inflate(R.layout.ip_address_dialog, null)
        alertDialog.setView(myView)
        myView.ip_dialog.hint = pref.ipAddress
        myView.ip_dialog_change.setOnClickListener({
            if (Util.isValidIp(myView.ip_dialog.text.toString())){
                pref.ipAddress = myView.ip_dialog.text.toString()
                alertDialog.dismiss()
                updateInfo()
            }else
                Toast.makeText(context , "Enter Valid IP" , Toast.LENGTH_SHORT).show()
        })
        myView.ip_dialog_cancel.setOnClickListener({
            alertDialog.dismiss()
        })
        alertDialog.show()
    }


    private fun showPortDialog() {
        val alertDialog = AlertDialog.Builder(context).create()
        val myView = layoutInflater.inflate(R.layout.port_number_dialog, null)
        alertDialog.setView(myView)
        myView.port_dialog.hint = pref.portNumber.toString()
        myView.port_dialog_change.setOnClickListener({
            if (!myView.port_dialog.text.toString().equals("")){
                pref.portNumber = Integer.parseInt(myView.port_dialog.text.toString())
                alertDialog.dismiss()
                updateInfo()
            }else {
                Toast.makeText(context , "Enter port number" , Toast.LENGTH_SHORT).show()
            }
        })
        myView.port_dialog_cancel.setOnClickListener({
            alertDialog.dismiss()
        })
        alertDialog.show()
    }

    private fun updateInfo(){
        ipAddress.text = pref.ipAddress
        portNumber.text = pref.portNumber.toString()
    }

    companion object {

        fun newInstance(): FloatingMenuFragment {
            return FloatingMenuFragment()
        }
    }


}

