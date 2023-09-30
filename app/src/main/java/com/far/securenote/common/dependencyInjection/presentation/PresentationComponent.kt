package com.far.securenote.common.dependencyInjection.presentation

import com.far.securenote.MainActivity
import com.far.securenote.view.BulkInsertNote
import com.far.securenote.view.Login
import com.far.securenote.view.MainScreen
import com.far.securenote.view.Notes
import dagger.Subcomponent

@PresentationScope //if a component.dependency is scoped, you need to scope the component too.
@Subcomponent(modules = [PresentationModule::class])
interface PresentationComponent {

    fun inject(loginActivity: Login)
    fun inject(mainActivity: MainActivity)
    fun inject(mainScreenFragment: MainScreen)
    fun inject(notesFragment: Notes)
    fun inject(bulkInsertActivity: BulkInsertNote)

}