package com.far.securenote.view.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.far.securenote.common.dependencyInjection.activity.ActivityComponent
import com.far.securenote.common.dependencyInjection.activity.ActivityModule
import com.far.securenote.common.dependencyInjection.presentation.PresentationComponent
import com.far.securenote.common.dependencyInjection.presentation.PresentationModule

open class BaseActivity: AppCompatActivity() {

    private val applicationComponent get() = (application as MyApplication).applicationModule
    //for fragments
    val activityComponent: ActivityComponent by lazy{
        applicationComponent.newActivityComponent(ActivityModule(this))

    }

    protected val presentationComponent: PresentationComponent by lazy{
        activityComponent.newPresentationComponent(PresentationModule())
    }


     fun showToast(text: String){
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
     }


}