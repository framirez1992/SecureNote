package com.far.securenote.common.dependencyInjection.application

import com.far.securenote.common.dependencyInjection.activity.ActivityComponent
import com.far.securenote.common.dependencyInjection.activity.ActivityModule
import com.far.securenote.view.common.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun newActivityComponent(activityModule: ActivityModule):ActivityComponent

}