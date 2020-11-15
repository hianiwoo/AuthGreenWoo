package com.greenwoo.data.repository

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.greenwoo.data.common.await
import com.greenwoo.domain.models.User
import com.greenwoo.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import java.io.File
import java.util.*
import kotlin.coroutines.resume

class UserRepositoryImpl : UserRepository {

    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance().reference

    override fun getUser() = flow {
        val uid = auth.currentUser!!.uid
        val reff = database.child("users").child(uid)
        var listener: ValueEventListener? = null
        val user = suspendCancellableCoroutine<User> { c ->
            listener = reff.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val params = snapshot.children.toList()
                    if (params.find { it.key == "id" } == null) return
                    Timber.d("params $params")
                    val user = User(
                        params.find { it.key == "id" }?.value.toString(),
                        params.find { it.key == "nickname" }?.value.toString(),
                        params.find { it.key == "url" }?.value.toString(),
                        params.find { it.key == "mail" }?.value.toString()
                    )
                    c.resume(user)
                    c.cancel()
                }

                override fun onCancelled(error: DatabaseError) {
                    c.cancel()
                }
            })
            database.addListenerForSingleValueEvent(listener ?: return@suspendCancellableCoroutine)
        }
        reff.removeEventListener(listener ?: return@flow)
        emit(user)
    }

    override fun updateUser(user: User) = flow {
        auth.currentUser?.let {
            it.updateProfile(
                UserProfileChangeRequest.Builder().setDisplayName(user.nickname).build()
            ).await()
            it.updateEmail(user.mail).await()
            val uid = it.uid
            val database = FirebaseDatabase.getInstance().reference
            database.child("users").child(uid).setValue(user)
        }
        emit(Unit)
    }

    override fun uploadImage(file: File) = flow {
        val ref = storage.child("images/" + UUID.randomUUID().toString())
        val task = suspendCancellableCoroutine<Task<Uri>> { c ->
            ref.putFile(Uri.fromFile(file))
                .addOnCompleteListener {
                    val isComplite =
                        it.result?.metadata?.reference?.downloadUrl?.isComplete ?: false
                    if (it.isComplete) {
                        c.resume(ref.downloadUrl)
                    }
                }.addOnCanceledListener { c.cancel() }
        }
        val url = task.await()
        emit(url.toString())
    }.flatMapLatest { savePhotoProfile(it) }

    private fun savePhotoProfile(url: String) = getUser().flatMapLatest { user ->
        flow {
            val uid = auth.currentUser?.uid ?: throw Exception("uid null")
            val database = FirebaseDatabase.getInstance().reference
            val newUser = user.copy(url = url)
            database.child("users").child(uid).setValue(newUser)
            emit(newUser)
        }
    }
}