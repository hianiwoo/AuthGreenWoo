package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute() = authRepository.checkAuth()
}