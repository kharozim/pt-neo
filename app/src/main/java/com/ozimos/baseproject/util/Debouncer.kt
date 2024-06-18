package com.ozimos.baseproject.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Debouncer<T>(private val delayMillis: Long = 500L, private val onDebounce: (T) -> Unit) {
    private var debounceJob: Job? = null

    fun submit(value : T) {
        debounceJob?.cancel()
        debounceJob = GlobalScope.launch {
            delay(delayMillis)
            onDebounce(value)
        }
    }
}