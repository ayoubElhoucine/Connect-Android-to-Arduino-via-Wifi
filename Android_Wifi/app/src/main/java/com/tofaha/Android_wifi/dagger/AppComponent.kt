package com.tofaha.Android_wifi.dagger

import com.tofaha.Android_wifi.ui.FloatingMenuFragment
import com.tofaha.Android_wifi.ui.InfoActivity
import com.tofaha.Android_wifi.ui.main.MainActivity

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(AppModule::class , PreferenceModule :: class))
interface AppComponent {

    fun inject(infoActivity: InfoActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(target: FloatingMenuFragment)

}
