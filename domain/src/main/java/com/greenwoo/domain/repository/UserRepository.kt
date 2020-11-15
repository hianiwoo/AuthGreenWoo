package com.greenwoo.domain.repository

import com.greenwoo.domain.models.User
import kotlinx.coroutines.flow.Flow
import java.io.File

interface UserRepository {

    fun getUser(): Flow<User>
    fun uploadImage(file: File): Flow<User>
    fun updateUser(user: User): Flow<Unit>
}