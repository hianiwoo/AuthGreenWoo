package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.ModuleRepository
import javax.inject.Inject

class AddModuleInteractor @Inject constructor(
    private val repository: ModuleRepository
) {
    fun execute(key: String, uid: String, name: String, description: String) =
        repository.insertModule(key, uid, name, description)
}