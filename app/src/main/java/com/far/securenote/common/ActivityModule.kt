package com.far.securenote.common

import com.far.securenote.model.NoteService
import com.far.securenote.view.common.BaseActivity


class ActivityModule(
    private val baseActivity: BaseActivity,
    private val applicationModule:ApplicationModule) {

    val biometricManager by lazy {
        BiometricManager(baseActivity)
    }
    val screenNavigator by lazy {
        ScreenNavigator(baseActivity)
    }
    val fileManager by lazy {
        FileManager(baseActivity)
    }

    val fireBaseDB get() = applicationModule.fireBaseDB
}