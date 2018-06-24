package com.github.magneticflux.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(InstantTaskExecutorExtension::class)
class LiveDataUnitTest {

    @Test
    fun `when two values are zipped together with zipTo, they ignore nulls`() {
        val v1 = MutableLiveData<String>()
        val v2 = MutableLiveData<String>()

        val pair = v1 zipTo v2

        v1.value = null
        v2.value = null
        Assertions.assertEquals(null, pair.awaitValue)

        v1.value = "Hello"
        v2.value = "world"
        Assertions.assertEquals("Hello" to "world", pair.awaitValue)

        v1.value = "Goodbye"
        Assertions.assertEquals("Goodbye" to "world", pair.awaitValue)
    }

    @Test
    fun `when two values are zipped together with zipToNullable, they encounter nulls`() {
        val v1 = MutableLiveData<String>()
        val v2 = MutableLiveData<String>()

        val pair = v1 zipToNullable v2

        v1.value = null
        v2.value = null
        Assertions.assertEquals(null to null, pair.awaitValue)

        v1.value = "Hello"
        v2.value = "world"
        Assertions.assertEquals("Hello" to "world", pair.awaitValue)

        v1.value = "Goodbye"
        Assertions.assertEquals("Goodbye" to "world", pair.awaitValue)
    }

    @Test
    fun `when a null value is passed to a map, null is returned`() {
        val nullable = MutableLiveData<String>()
        val mapped = nullable.map { "$it world!" }

        nullable.value = null
        Assertions.assertNull(mapped.awaitValue)

        nullable.value = "Hello"
        Assertions.assertEquals("Hello world!", mapped.awaitValue)
    }

    @Test
    fun `when a null value is passed to a mapNullable, null is encountered`() {
        val nullable = MutableLiveData<String>()
        val mapped = nullable.mapNullable { "$it world!" }

        nullable.value = null
        Assertions.assertEquals("null world!", mapped.awaitValue)

        nullable.value = "Hello"
        Assertions.assertEquals("Hello world!", mapped.awaitValue)
    }

    @Test
    fun `when a null value is passed to a switchMap, null is returned`() {
        val nullable = MutableLiveData<String>()

        val data = MutableLiveData<String>().apply { value = "Hello world!" }
        val backup = MutableLiveData<String>().apply { value = "Error!" }

        val mapped = nullable.switchMap {
            if (it == null)
                backup
            else
                data
        }

        nullable.value = null
        Assertions.assertNull(mapped.awaitValue)

        nullable.value = "Hello world!"
        Assertions.assertEquals("Hello world!", mapped.awaitValue)
    }

    @Test
    fun `when a null value is passed to a switchMapNullable, null is encountered`() {
        val nullable = MutableLiveData<String>()

        val data = MutableLiveData<String>().apply { value = "Hello world!" }
        val backup = MutableLiveData<String>().apply { value = "Error!" }

        val mapped = nullable.switchMapNullable {
            if (it == null)
                backup
            else
                data
        }

        nullable.value = null
        Assertions.assertEquals("Error!", mapped.awaitValue)

        nullable.value = "Hello world!"
        Assertions.assertEquals("Hello world!", mapped.awaitValue)
    }

    @Test
    fun `when a bidirectional map is established, changes flow in both directions`() {

        val aAndB = MediatorLiveData<Pair<String, String>>()
        val a = aAndB.bidiMap(
                { pair: Pair<String, String>, _: String? ->
                    pair.first
                },
                { oldPair: Pair<String, String>, newString: String ->
                    oldPair.copy(first = newString)
                }
        )
        val b = aAndB.bidiMap(
                { pair: Pair<String, String>, _: String? ->
                    pair.second
                },
                { oldPair: Pair<String, String>, newString: String ->
                    oldPair.copy(second = newString)
                }
        )


        aAndB.value = "abc" to "123"

        Assertions.assertEquals(a.awaitValue, "abc")
        Assertions.assertEquals(b.awaitValue, "123")

        a.value = "xyz"

        Assertions.assertEquals(aAndB.awaitValue, "xyz" to "123")

        b.value = "789"

        Assertions.assertEquals(aAndB.awaitValue, "xyz" to "789")
    }
}