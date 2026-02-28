package com.asoncs.kmp2048.di

import com.asoncs.kmp2048.data.local.GameLocalDataSource
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

val platformModule = module {
    single<Settings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }

    single {
        GameLocalDataSource(settings = get())
    }
}
