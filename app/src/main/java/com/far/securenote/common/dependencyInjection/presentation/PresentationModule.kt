package com.far.securenote.common.dependencyInjection.presentation

import com.far.securenote.common.dependencyInjection.activity.ActivityComponent
import com.far.securenote.model.services.NoteService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class PresentationModule() {

    @Provides
    fun noteService(firebaseFireStore:FirebaseFirestore) = NoteService(firebaseFireStore)
}