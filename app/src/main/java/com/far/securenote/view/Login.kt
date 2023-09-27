package com.far.securenote.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.biometric.BiometricPrompt
import com.far.securenote.MainActivity
import com.far.securenote.R
import com.far.securenote.common.BiometricManager
import com.far.securenote.databinding.ActivityLoginBinding
import com.far.securenote.view.common.BaseActivity

class Login : BaseActivity() {
    private lateinit var _binding:ActivityLoginBinding
    private lateinit var biometricManager:BiometricManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        biometricManager = presentationModule.biometricManager()
        biometricManager.setListeners(object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int,errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                showToast(getString(R.string.authentication_error).plus(": $errString"))
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                loginSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                showToast(getString(R.string.authentication_failed))
            }
        })



        _binding.btnLogin.setOnClickListener {
            presentationModule.biometricManager()
            biometricManager.checkAuthenticationAvailability()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == BiometricManager.BIOMETRIC_ENROLLMENT_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            biometricManager.requestBiometricAuth()
        }
    }

    private fun loginSuccess(){
        var i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }



}