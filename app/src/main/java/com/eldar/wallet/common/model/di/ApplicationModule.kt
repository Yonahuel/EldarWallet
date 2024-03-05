package com.eldar.wallet.common.model.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationModule: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}