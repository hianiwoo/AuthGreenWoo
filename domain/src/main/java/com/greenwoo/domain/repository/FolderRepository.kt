package com.greenwoo.domain.repository

import com.greenwoo.domain.models.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {

    fun insertFolder(key: String?, uid: String, name: String, description: String): Flow<String>
    fun getFolder(key: String?): Flow<Folder>
    fun updateFolder(folder: Folder): Flow<Unit>
    fun getListFolder(): Flow<List<Folder>>
}