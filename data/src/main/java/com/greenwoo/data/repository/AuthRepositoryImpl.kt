package com.greenwoo.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.greenwoo.data.common.await
import com.greenwoo.domain.models.User
import com.greenwoo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class AuthRepositoryImpl : AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    override fun signUp(mail: String, password: String, nickname: String) = flow {
        auth.createUserWithEmailAndPassword(mail, password).await()
        auth.currentUser?.let {
            it.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(nickname).build())
                .await()
            val uid = it.uid
            val database = FirebaseDatabase.getInstance().reference
            val user = User(uid, nickname, "", mail)
            database.child("users").child(uid).setValue(user)
        }
        emit(Unit)
    }

    override fun signIn(nickname: String, password: String) = flow {
        auth.signInWithEmailAndPassword(nickname, password).await()
        emit(Unit)
    }

    override fun checkAuth() = flow {
        try {
            auth.currentUser?.reload()?.await()
        } catch (e: Exception){}
        emit(auth.currentUser != null)
    }

    override fun forgotPassword(email: String) = flow {
        auth.sendPasswordResetEmail(email).await()
        emit(Unit)
    }

    override fun signOut() = flow {
        auth.signOut()
        emit(Unit)
    }
}