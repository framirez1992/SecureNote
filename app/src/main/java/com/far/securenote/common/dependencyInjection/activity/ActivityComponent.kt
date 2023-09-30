package com.far.securenote.common.dependencyInjection.activity

import com.far.securenote.common.dependencyInjection.presentation.PresentationComponent
import com.far.securenote.common.dependencyInjection.presentation.PresentationModule
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun newPresentationComponent(presentationModule: PresentationModule):PresentationComponent
}