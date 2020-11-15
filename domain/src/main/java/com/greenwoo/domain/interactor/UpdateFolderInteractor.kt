package com.greenwoo.domain.interactor

import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.repository.FolderRepository
import javax.inject.Inject

class UpdateFolderInteractor @Inject constructor(
    private val folderRepository: FolderRepository
) {
    fun execute(folder: Folder) = folderRepository.updateFolder(folder)
}