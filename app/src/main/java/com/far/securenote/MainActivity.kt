package com.far.securenote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.far.securenote.common.ScreenNavigator
import com.far.securenote.databinding.ActivityMainBinding
import com.far.securenote.view.MainScreen
import com.far.securenote.view.common.BaseActivity

class MainActivity : BaseActivity() {

    private lateinit var _binding:ActivityMainBinding
    private lateinit var screenNavigator:ScreenNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        screenNavigator = presentationModule.screenNavigator

        if (savedInstanceState == null) {
            screenNavigator.MainScren(_binding.frame.id)
        }
    }

    override fun onBackPressed() {
     screenNavigator.onBackPressed(R.id.frame)
    }
}