package com.far.securenote.common.dependencyInjection.activity

import com.far.securenote.common.BiometricManager
import com.far.securenote.common.FileManager
import com.far.securenote.common.ScreenNavigator
import com.far.securenote.common.dependencyInjection.application.ApplicationComponent
import com.far.securenote.view.common.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(
    private val baseActivity: BaseActivity
) {

    @Provides
    fun activity() = baseActivity

    @Provides
    @ActivityScope
    fun biometricManager(activity: BaseActivity) = BiometricManager(activity)

    @Provides
    @ActivityScope
    fun screenNavigator(activity: BaseActivity)= ScreenNavigator(activity)
    @Provides
    @ActivityScope
    fun  fileManager(activity: BaseActivity) = FileManager(activity)


}