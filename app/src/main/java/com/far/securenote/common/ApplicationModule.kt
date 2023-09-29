package com.far.securenote.common

import com.far.securenote.view.common.MyApplication
import com.google.firebase.firestore.FirebaseFirestore

class ApplicationModule(application:MyApplication) {

    val fireBaseDB:FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

}