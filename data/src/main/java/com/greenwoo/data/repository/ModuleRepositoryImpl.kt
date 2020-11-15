package com.greenwoo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.greenwoo.data.common.await
import com.greenwoo.data.common.toFlow
import com.greenwoo.data.common.value
import com.greenwoo.data.mapper.toDomain
import com.greenwoo.data.models.ModuleData
import com.greenwoo.domain.models.Module
import com.greenwoo.domain.repository.ModuleRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ModuleRepositoryImpl : ModuleRepository {

    private val database = FirebaseDatabase.getInstance().reference
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: "uid null"

    override fun insertModule(key: String?, uid: String, name: String, description: String) = flow {
        val module = Module(key, uid, name, description)
        emit(database.child("modules").push().value(module))
    }

    override fun getModule(key: String?) = flow {
        emit(
            database
                .child("modules")
                .child(key ?: "")
                .await<ModuleData>()
                .toDomain()
        )
    }

    override fun updateModule(module: Module) = flow {
        database.child("modules").push().setValue(module)
        emit(Unit)
    }

    override fun getListModule() = database.child("modules")
        .orderByChild("uid")
        .equalTo(uid)
        .ref
        .toFlow<HashMap<String, ModuleData>>()
        .map { map ->
            map.map { it.value.apply { key = it.key } }
                .map { it.toDomain() }
        }
}