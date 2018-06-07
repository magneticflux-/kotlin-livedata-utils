package com.github.magneticflux.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * Waits a ***very*** short amount of time for a value to be present. Should be used with [InstantTaskExecutorExtension].
 */
val <T> LiveData<T>.awaitValue: T?
    get() {
        var data: T? = null
        val latch = CountDownLatch(1)

        lateinit var observer: Observer<T>
        observer = Observer { o ->
            data = o
            latch.countDown()
            removeObserver(observer)
        }

        this.observeForever(observer)

        latch.await(1, TimeUnit.MILLISECONDS)

        @Suppress("UNCHECKED_CAST")
        return data
    }