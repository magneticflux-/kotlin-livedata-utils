package com.github.magneticflux.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations

/**
 * This function creates a [LiveData] of a [Pair] of the two types provided. The resulting LiveData is updated whenever either input LiveData updates and both LiveData have updated at least once before.
 *
 * If the zip of A and B is C, and A and B are updated in this pattern: `AABA`, C would be updated twice (once with the second A value and first B value, and once with the third A value and first B value).
 *
 * @param a the first LiveData
 * @param b the second LiveData
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
fun <A, B> zipLiveData(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = Pair(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }
}

/**
 * This is merely an extension function for [zipLiveData].
 *
 * @see zipLiveData
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.zipTo(b: LiveData<B>): LiveData<Pair<A, B>> = zipLiveData(this, b)

/**
 * This is an extension function that calls to [Transformations.map]. If null is received, null is returned instead of calling the provided function.
 *
 * @see Transformations.map
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
inline fun <A, B> LiveData<A>.map(crossinline function: (A) -> B): LiveData<B> =
        Transformations.map(this) { it: A? ->
            if (it == null) null else function(it)
        }

/**
 * This is an extension function that calls to [Transformations.map]. It exposes the possibilities of receiving and returning null.
 *
 * @see Transformations.map
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.mapNullable(function: (A?) -> B?): LiveData<B> =
        Transformations.map(this, function)

/**
 * This is an extension function that calls to [Transformations.switchMap]. If null is received, null is returned instead of calling the provided function.
 *
 * @see Transformations.switchMap
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.switchMap(function: (A) -> LiveData<B>): LiveData<B> =
        Transformations.switchMap(this) {
            if (it == null) null else function(it)
        }

/**
 * This is an extension function that calls to [Transformations.switchMap]. It exposes the possibilities of receiving and returning null.
 *
 * @see Transformations.switchMap
 * @since 0.1.0
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.switchMapNullable(function: (A?) -> LiveData<B>?): LiveData<B> =
        Transformations.switchMap(this, function)
