package com.far.securenote.common
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import com.far.securenote.view.common.BaseActivity
import androidx.biometric.BiometricPrompt
import com.far.securenote.R

open class BiometricManager(
    var activity: BaseActivity) {

     companion object{
         const val BIOMETRIC_ENROLLMENT_REQUEST_CODE:Int=1
     }
    private val executor by lazy {
        ContextCompat.getMainExecutor(activity)
    }
    private val promptInfo by lazy {
        BiometricPrompt.PromptInfo.Builder()
            .setTitle(activity.getString(R.string.biometric_login))
            .setSubtitle(activity.getString(R.string.login_using))
            //.setNegativeButtonText("Cancel")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()
    }
    private lateinit var biometricPrompt:BiometricPrompt

    fun setListeners(callback:BiometricPrompt.AuthenticationCallback){
        biometricPrompt = BiometricPrompt(activity, executor,callback)
    }
    fun requestBiometricAuth(){
        biometricPrompt.authenticate(promptInfo)
    }

     fun checkAuthenticationAvailability(){
        val biometricManager = BiometricManager.from(activity)
        when (biometricManager.canAuthenticate( BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> requestBiometricAuth()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                activity.showToast(activity.getString(R.string.no_biometric_features_available))
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                activity.showToast(activity.getString(R.string.biometric_features_currently_unavailable))
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(
                            Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    }

                    activity.startActivityForResult(enrollIntent, BIOMETRIC_ENROLLMENT_REQUEST_CODE)


                } else {
                    activity.showToast(activity.getString(R.string.no_biometric_info))
                }

            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                activity.showToast(activity.getString(R.string.biometric_error_security_update_required))
            }
            else -> {
                activity.showToast(activity.getString(R.string.unknown_error))
            }
        }
    }


}