package com.far.securenote.common

import com.far.securenote.view.common.BaseActivity


class ActivityModule(val baseActivity: BaseActivity) {

    val activity get() = baseActivity
    fun biometricManager()= BiometricManager(activity)
}