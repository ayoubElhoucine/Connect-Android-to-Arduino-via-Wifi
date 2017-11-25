package com.tofaha.esp8266.dagger

import com.tofaha.esp8266.ui.FloatingMenuFragment
import com.tofaha.esp8266.ui.InfoActivity
import com.tofaha.esp8266.ui.MainActivity

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = arrayOf(AppModule::class , PreferenceModule :: class))
interface AppComponent {

    fun inject(infoActivity: InfoActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(target: FloatingMenuFragment)

}
