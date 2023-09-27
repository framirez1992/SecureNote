package com.far.securenote.common

import com.far.securenote.view.common.BaseActivity
//import dagger.Module
//import dagger.Provides

//@Module
class PresentationModule(val activityModule:ActivityModule) {

    //@Provides
    fun activity() = activityModule.activity

    //@Provides
    fun biometricManager()= activityModule.biometricManager()

}