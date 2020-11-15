package com.greenwoo.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun signUp(mail: String, password: String, nickname: String): Flow<Unit>
    fun signIn(nickname: String, password: String): Flow<Unit>
    fun signOut(): Flow<Unit>
    fun checkAuth(): Flow<Boolean>
    fun forgotPassword(mail: String): Flow<Unit>
}