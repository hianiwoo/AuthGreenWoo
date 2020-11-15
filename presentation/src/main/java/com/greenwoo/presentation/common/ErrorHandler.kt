package com.greenwoo.presentation.common

import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.greenwoo.presentation.R
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor() {

    fun handelError(
        exception: Throwable,
        onLocalMessageError: ((Int) -> Unit)? = null
    ){
        Timber.e(exception)
        when(exception){
            is FirebaseAuthException -> onLocalMessageError?.invoke(handleAuthError(exception))
            is FirebaseException -> onLocalMessageError?.invoke(0)
            else -> onLocalMessageError?.invoke(R.string.unknow_error)
        }
    }

    private fun handleAuthError(exception: FirebaseAuthException) : Int {
        return when(exception){
            is FirebaseAuthInvalidCredentialsException -> R.string.signin_error_incorrect_user_data
            is FirebaseAuthInvalidUserException -> R.string.signin_error_no_registered
            is FirebaseAuthWeakPasswordException -> R.string.signin_error_no_registered
            is FirebaseAuthRecentLoginRequiredException -> R.string.setting_error_re_entry
            is FirebaseAuthUserCollisionException -> R.string.setting_error_busy_mail
            else -> R.string.auth_unknow_error
        }
    }
}