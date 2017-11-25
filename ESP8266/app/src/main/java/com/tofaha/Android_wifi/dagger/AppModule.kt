package com.tofaha.Android_wifi.dagger

import android.app.Application
import android.content.Context

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return this.application
    }
}
