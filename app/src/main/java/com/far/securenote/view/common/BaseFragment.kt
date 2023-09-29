package com.far.securenote.view.common

import androidx.fragment.app.Fragment
import com.far.securenote.common.PresentationModule

open class BaseFragment: Fragment() {
    //fragment use its own
    val presentationModule by lazy {
        PresentationModule((activity as BaseActivity).activityModule)
    }
}