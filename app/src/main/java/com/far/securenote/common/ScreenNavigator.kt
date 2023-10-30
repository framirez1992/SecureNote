package com.far.securenote.common

import androidx.fragment.app.FragmentTransaction
import com.far.securenote.R
import com.far.securenote.model.Note
import com.far.securenote.view.MainScreen
import com.far.securenote.view.Notes
import com.far.securenote.view.common.BaseActivity
import com.far.securenote.view.common.BaseFragment

class ScreenNavigator(
    private val baseActivity: BaseActivity
) {

    private val activity get() = baseActivity
    private val fragmentManager get() = activity.supportFragmentManager
    private lateinit var currentFragment:BaseFragment

    fun MainScren(containerViewId: Int){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        currentFragment = MainScreen.newInstance()
        fragmentTransaction.replace(containerViewId, currentFragment).commit()
    }
    fun Notes(containerViewId: Int, note: Note?){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        currentFragment = Notes.newInstance(note)
        fragmentTransaction.replace(containerViewId, currentFragment).commit()
    }

    fun onBackPressed(containerViewId: Int){
        if(currentFragment is MainScreen){
            activity.finish()
        }else if(currentFragment is Notes){
            MainScren(containerViewId)
        }
    }
}