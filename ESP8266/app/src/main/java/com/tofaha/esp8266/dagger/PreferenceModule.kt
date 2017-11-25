package com.tofaha.esp8266.dagger

import android.content.Context
import com.tofaha.esp8266.Pref
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ayoub on 11/23/17.
 */
@Module
class PreferenceModule {

    @Singleton
    @Provides
    internal fun providePref(context: Context): Pref {
        return Pref(context)
    }

}