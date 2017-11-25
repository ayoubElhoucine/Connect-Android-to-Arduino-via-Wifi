package com.tofaha.Android_wifi.dagger

import android.content.Context
import com.tofaha.Android_wifi.Pref
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