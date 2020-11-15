package com.greenwoo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.greenwoo.data.common.await
import com.greenwoo.data.common.toFlow
import com.greenwoo.data.common.value
import com.greenwoo.data.mapper.toDomain
import com.greenwoo.data.models.FolderData
import com.greenwoo.domain.models.Folder
import com.greenwoo.domain.repository.FolderRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FolderRepositoryImpl : FolderRepository {

    private val database = FirebaseDatabase.getInstance().reference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "uid null"

    override fun insertFolder(key: String?, uid: String, name: String, description: String) = flow {
        val folder = Folder(key, uid, name, description)
        emit(database.child("folders").push().value(folder))
    }

    override fun getFolder(key: String?) = flow {
        emit(
            database
                .child("folders")
                .child(key ?: "")
                .await<FolderData>()
                .toDomain()
        )
    }

    override fun updateFolder(folder: Folder) = flow {
        database.child("folders").push().setValue(folder)
        emit(Unit)
    }

    override fun getListFolder() = database.child("folders")
        .orderByChild("uid")
        .equalTo(uid)
        .ref
        .toFlow<HashMap<String, FolderData>>()
        .map { map ->
            map.map { it.value.apply { key = it.key } }
                .map { it.toDomain() }
        }
}