package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(email: String) = authRepository.forgotPassword(email)
}