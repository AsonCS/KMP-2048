package com.asoncs.kmp2048.di

import com.asoncs.kmp2048.data.local.GameLocalDataSource
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val platformModule = module {
    single<Settings> {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences("kmp2048_prefs", android.content.Context.MODE_PRIVATE)
        )
    }

    single {
        GameLocalDataSource(settings = get())
    }
}
