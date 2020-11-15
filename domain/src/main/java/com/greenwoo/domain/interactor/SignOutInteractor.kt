package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute() = authRepository.signOut()
}