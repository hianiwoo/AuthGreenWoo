package com.greenwoo.data.repository

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context
import com.greenwoo.data.common.toFlow
import com.greenwoo.domain.repository.ConnectRepository


class ConnectRepositoryImpl : ConnectRepository {

    override fun connect() =
        FirebaseDatabase.getInstance().getReference(".info/connected").toFlow<Boolean>()

}