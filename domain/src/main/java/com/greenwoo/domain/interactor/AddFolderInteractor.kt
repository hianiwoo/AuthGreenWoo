package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.FolderRepository
import javax.inject.Inject

class AddFolderInteractor @Inject constructor(
    private val repository: FolderRepository
) {
    fun execute(key: String, uid: String, name: String, description: String) =
        repository.insertFolder(key, uid, name, description)
}