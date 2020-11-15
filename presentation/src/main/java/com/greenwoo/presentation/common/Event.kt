package com.greenwoo.presentation.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

data class Event<T>(private val value: T) {

    @Suppress("DataClassShouldBeImmutable")
    private var wasConsumed = false

    @MainThread
    fun consume(consumer: (T) -> Unit) {
        if (!wasConsumed) {
            wasConsumed = true
            consumer(value)
        }
    }
}

fun <T : Any> T.toEvent() = Event(this)

fun <T : Any> MutableLiveData<Event<T>>.postEvent(event: T) = this.postValue(event.toEvent())

@MainThread
fun <T> LiveData<Event<T>>.observeEvent(
    owner: LifecycleOwner,
    onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t -> t.consume(onChanged) }
    observe(owner, wrappedObserver)
    return wrappedObserver
}