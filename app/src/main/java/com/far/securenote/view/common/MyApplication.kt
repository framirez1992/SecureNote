package com.far.securenote.view.common

import android.app.Application
import com.far.securenote.common.dependencyInjection.application.ApplicationComponent
import com.far.securenote.common.dependencyInjection.application.ApplicationModule
import com.far.securenote.common.dependencyInjection.application.DaggerApplicationComponent

open class MyApplication: Application() {


    lateinit var applicationModule: ApplicationComponent

    override fun onCreate() {
        applicationModule =  DaggerApplicationComponent.builder().
        applicationModule(ApplicationModule(this))
            .build()
        super.onCreate()
    }
}