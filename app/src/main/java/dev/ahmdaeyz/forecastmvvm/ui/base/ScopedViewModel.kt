package dev.ahmdaeyz.forecastmvvm.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class ScopedViewModel: ViewModel(),CoroutineScope {
    abstract val viewModelJob: Job
    override val coroutineContext: CoroutineContext
        get() = viewModelJob + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}