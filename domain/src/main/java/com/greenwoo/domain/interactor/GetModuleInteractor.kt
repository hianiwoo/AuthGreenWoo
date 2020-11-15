package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.ModuleRepository
import javax.inject.Inject

class GetModuleInteractor @Inject constructor(
    private val repository: ModuleRepository
) {
    fun execute(key: String?) = repository.getModule(key)
}