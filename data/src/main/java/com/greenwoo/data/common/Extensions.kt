package com.greenwoo.data.common

import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Coroutine support to Firebase Task interface
 *
 * This method is not intended to be used in your code. You should probably use [await] sinsce it's
 * more idiomatic.
 *
 * The implementation is pretty simple. It justs use a [suspendCoroutine] to encapsulate the
 * Firebase [com.google.android.gms.tasks.OnCompleteListener] interface.
 *
 */
private suspend fun <T> awaitTask(task: Task<T>): T = suspendCancellableCoroutine { cont ->
    task
        .addOnSuccessListener { cont.resume(it) }
        .addOnFailureListener {
            cont.resumeWithException(it)
        }
        .addOnCanceledListener {
            cont.resumeWithException(CancellationException("Cancelled"))
        }
}

/**
 * Coroutine support to Firebase Task interface
 *
 * This extension function allows you to interact with a Firebase [com.google.android.gms.tasks.Task]
 * using the `await()` method instead of the standard listeners.
 *
 * There is a sample code below comparing the two approaches. Assuming that
 * `auth` variable has the value returned from `FirebaseAuth.getInstance()`
 * method call then your code can be something like:
 *
 * ```
 * auth.getUserByEmail(email)
 *   .addOnSuccessListener { user -> println(user) }
 *   .addOnFailureListener { exception -> println(exception) }
 * ```
 *
 * When using the coroutine approach, it should be more like this:
 *
 * ```
 * try {
 *   val user = auth.getUserByEmail(email).await()
 *   println(user)
 * } catch (exception: Exception) {
 *   println(exception)
 * }
 * ```
 *
 * @param T The type of the value been returned
 * @throws Exception Thrown in case of network error or other reasons described in the Firebase docs
 * @return The value returned by the Firebase success callback
 */
suspend fun <T> Task<T>.await(): T = awaitTask(this)

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
suspend fun DatabaseReference.value(value: Any) = suspendCancellableCoroutine<String> {
    setValue(value) { error, ref ->
        if (error == null && ref.key != null) {
            it.resume(ref.key!!)
        } else {
            it.cancel(error?.toException())
        }
    }
}

suspend inline fun <reified T : Any> DatabaseReference.await() = suspendCoroutine<T> { cont ->
    addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(data: DataSnapshot) {
            try {
                cont.resume(data.getValue(T::class.java)!!)
            } catch (e: Throwable) {
                cont.resumeWithException(e)
            }
        }

        override fun onCancelled(error: DatabaseError) {
            cont.resumeWithException(error.toException())
        }
    })
}

inline fun <reified T : Any> DatabaseReference.toFlow() = flow {
    val value = suspendCancellableCoroutine<T> { cont ->
        addValueEventListener(object : ValueEventListener {
            override fun onDataChange(data: DataSnapshot) {
                if(!cont.isActive) return
                try {
                    cont.resume(data.getValue(object : GenericTypeIndicator<T>() {})!!)
                } catch (e: Throwable) {
                    cont.resumeWithException(e)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                cont.cancel(error.toException())
            }

        })
    }
    emit(value)
}