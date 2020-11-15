package com.greenwoo.domain.interactor

import com.greenwoo.domain.models.Module
import com.greenwoo.domain.repository.ModuleRepository
import javax.inject.Inject

class UpdateModuleInteractor @Inject constructor(
    private val moduleRepository: ModuleRepository
) {
    fun execute(moduleRemote: Module) = moduleRepository.updateModule(moduleRemote)
}