package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.AuthRepository
import javax.inject.Inject

class SignInInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(nickname: String, password: String) = authRepository.signIn(nickname, password)
}