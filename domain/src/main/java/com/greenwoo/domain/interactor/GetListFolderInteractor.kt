package com.greenwoo.domain.interactor

import com.greenwoo.domain.repository.FolderRepository
import javax.inject.Inject

class GetListFolderInteractor @Inject constructor(
    private val repository: FolderRepository
) {
    fun execute() = repository.getListFolder()
}