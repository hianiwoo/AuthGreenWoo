package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class GetUserInteractor @Inject constructor(
    private val repository: UserRepository
) {
    fun execute() = repository.getUser()
    fun uploadImage(file: File) = repository.uploadImage(file)
}