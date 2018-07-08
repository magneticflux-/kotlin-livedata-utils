package com.github.magneticflux.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Waits a ***very*** short amount of time for a value to be present. Must be used with [InstantTaskExecutorExtension].
 */
val <T> LiveData<T>.awaitValue: T?
    get() {

        lateinit var observer: Observer<T>
        observer = Observer {
            removeObserver(observer)
        }
        this.observeForever(observer)

        return this.value
    }