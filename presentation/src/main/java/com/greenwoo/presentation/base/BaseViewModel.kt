package com.greenwoo.presentation.base

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import com.greenwoo.presentation.BuildConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

abstract class BaseViewModel<View : Any> : ViewModel() {

    abstract val viewData: View

    private val supervisor = SupervisorJob()
    protected var viewModelScope = CoroutineScope(Dispatchers.IO + supervisor)
    protected val viewModelContext get() = viewModelScope.coroutineContext

    override fun onCleared() {
        super.onCleared()
        cancelBgJobs()
    }

    fun startBgJob(block: suspend CoroutineScope.() -> Unit): Job =
        viewModelScope.launch(block = {
            if (BuildConfig.DEBUG) delay(500)
            block.invoke(this)
        })

    private fun cancelBgJobs() {
        supervisor.cancelChildren()
    }

    @VisibleForTesting
    fun setTestScope(dispatcher: CoroutineDispatcher) {
        viewModelScope = CoroutineScope(dispatcher + supervisor)
    }

    protected fun <T> Flow<T>.launch() = launchIn(viewModelScope)
}