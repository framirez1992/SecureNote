package com.far.securenote.view.common

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.far.securenote.common.ActivityModule
import com.far.securenote.common.BiometricManager
import com.far.securenote.common.PresentationModule

open class BaseActivity: AppCompatActivity() {

    private val activityModule by lazy{
        ActivityModule(this)
    }
    val presentationModule by lazy{
        PresentationModule(activityModule)
    }


     fun showToast(text: String){
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
     }


}