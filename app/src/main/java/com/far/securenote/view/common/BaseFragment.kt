package com.far.securenote.view.common

import androidx.fragment.app.Fragment
import com.far.securenote.common.dependencyInjection.presentation.PresentationComponent
import com.far.securenote.common.dependencyInjection.presentation.PresentationModule

open class BaseFragment: Fragment() {
    //fragment use its own
    val presentationComponent: PresentationComponent by lazy {
        (activity as BaseActivity).activityComponent.newPresentationComponent(PresentationModule())
    }

}