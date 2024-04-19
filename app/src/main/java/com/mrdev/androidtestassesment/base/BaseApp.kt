package com.mrdev.androidtestassesment.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import java.io.File

@HiltAndroidApp
class BaseApp : Application(){
    companion object {
        lateinit var instance: BaseApp
        @JvmStatic
        fun get(): BaseApp {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
    }

}