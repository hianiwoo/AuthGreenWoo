package com.greenwoo.domain.interactor

import com.greenwoo.domain.models.User
import com.greenwoo.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserInteractor @Inject constructor(
    private val userRepository: UserRepository
) {
    fun execute(user: User) = userRepository.updateUser(user)
}