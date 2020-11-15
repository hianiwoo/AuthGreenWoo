package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.ModuleRepository
import javax.inject.Inject

class GetListModuleInteractor @Inject constructor(
    private val repository: ModuleRepository
) {
    fun execute() = repository.getListModule()
}