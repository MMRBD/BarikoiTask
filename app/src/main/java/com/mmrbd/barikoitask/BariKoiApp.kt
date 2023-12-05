package com.mmrbd.barikoitask

import android.app.Application
import com.mmrbd.barikoitask.di.networkModule
import com.mmrbd.barikoitask.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BariKoiApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BariKoiApp)
            modules(networkModule, viewModelModule)
        }
    }
}