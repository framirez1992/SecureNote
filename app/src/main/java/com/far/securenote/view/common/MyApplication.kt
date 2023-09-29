package com.far.securenote.view.common

import android.app.Application
import com.far.securenote.common.ApplicationModule

open class MyApplication: Application() {

    lateinit var applicationModule:ApplicationModule

    override fun onCreate() {
        applicationModule = ApplicationModule(this)
        super.onCreate()
    }
}