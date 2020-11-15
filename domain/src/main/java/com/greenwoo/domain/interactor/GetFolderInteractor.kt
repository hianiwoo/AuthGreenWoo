package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.FolderRepository
import javax.inject.Inject

class GetFolderInteractor @Inject constructor(
    private val repository: FolderRepository
) {
    fun execute(key: String?) = repository.getFolder(key)
}