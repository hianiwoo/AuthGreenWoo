package com.greenwoo.domain.repository

import com.greenwoo.domain.models.Module
import kotlinx.coroutines.flow.Flow

interface ModuleRepository {

    fun insertModule(key: String?, uid: String, name: String, description: String): Flow<String>
    fun getModule(key: String?): Flow<Module>
    fun updateModule(module: Module): Flow<Unit>
    fun getListModule(): Flow<List<Module>>
}