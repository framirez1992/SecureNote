package com.far.securenote.common.dependencyInjection.application

import com.far.securenote.view.common.MyApplication
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application:MyApplication) {

    @Provides
    fun application() = application
    @Provides
    @ApplicationScope
    fun fireBaseDB() = FirebaseFirestore.getInstance()

}