package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(email: String, password: String, nickname: String) =
        authRepository.signUp(email, password, nickname)
}