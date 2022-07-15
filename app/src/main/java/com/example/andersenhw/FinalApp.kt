package com.example.andersenhw

import android.app.Application
import com.example.andersenhw.di.DaggerApplicationComponent
import timber.log.Timber

class FinalApp : Application() {

    val appComponent = DaggerApplicationComponent
        .factory()
        .create(this)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}