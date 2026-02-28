package com.asoncs.kmp2048

import android.app.Application
import com.asoncs.kmp2048.di.appModule
import com.asoncs.kmp2048.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KMP2048Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KMP2048Application)
            modules(platformModule, appModule)
        }
    }
}
