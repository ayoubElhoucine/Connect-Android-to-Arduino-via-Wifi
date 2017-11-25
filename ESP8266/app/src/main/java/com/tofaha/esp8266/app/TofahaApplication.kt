package com.tofaha.esp8266.app

import android.app.Application
import com.tofaha.esp8266.dagger.AppComponent
import com.tofaha.esp8266.dagger.AppModule
import com.tofaha.esp8266.dagger.DaggerAppComponent

/**
 * Created by ayoub on 11/23/17.
 */
class TofahaApplication : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    protected fun initDagger(application: TofahaApplication): AppComponent {
        return DaggerAppComponent.builder().appModule(AppModule(application)).build()
    }

    fun getAppComponent(): AppComponent? {
        return this.appComponent
    }
}