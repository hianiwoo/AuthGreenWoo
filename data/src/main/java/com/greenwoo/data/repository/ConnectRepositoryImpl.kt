package com.greenwoo.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.greenwoo.data.common.toFlow
import com.greenwoo.domain.repository.ConnectRepository
import kotlinx.coroutines.flow.flow

class ConnectRepositoryImpl : ConnectRepository {

    override fun connect() = FirebaseDatabase.getInstance().getReference(".info/connected").toFlow<Boolean>()
}