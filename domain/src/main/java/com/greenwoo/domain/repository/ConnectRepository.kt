package com.greenwoo.domain.repository

import kotlinx.coroutines.flow.Flow

interface ConnectRepository {

    fun connect(): Flow<Boolean>
}