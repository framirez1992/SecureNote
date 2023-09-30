package com.far.securenote.common

import com.far.securenote.model.NoteService
import com.far.securenote.view.common.BaseActivity
//import dagger.Module
//import dagger.Provides

//@Module
class PresentationModule(private val activityModule:ActivityModule) {


    val biometricManager get() = activityModule.biometricManager
    val screenNavigator get() = activityModule.screenNavigator
    val fileManager get() = activityModule.fileManager
    val noteService  get() = NoteService(activityModule.fireBaseDB)
}