package com.aslibayar.foody

import android.app.Application
import com.aslibayar.network.ConnectivityMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ConnectivityMonitor(applicationContext)
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}