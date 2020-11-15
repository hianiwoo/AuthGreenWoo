package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.ConnectRepository
import javax.inject.Inject

class ConnectInteractor @Inject constructor(
    private val connectRepository: ConnectRepository
) {
    fun execute() = connectRepository.connect()
}