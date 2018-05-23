package amhk.chronos

import amhk.chronos.database.BlockEntity
import amhk.chronos.database.ChronosDatabase
import amhk.chronos.database.ID_NOT_IN_DATABASE

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.OffsetDateTime

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ChronosDatabaseTest {
    private lateinit var database: ChronosDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getContext()
        database = Room.inMemoryDatabaseBuilder(context, ChronosDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test(expected = IllegalArgumentException::class)
    fun failToCreateInvalidBlockEntity() {
        BlockEntity(ID_NOT_IN_DATABASE,
                OffsetDateTime.parse("2018-01-21T10:00:00.000Z"),
                OffsetDateTime.parse("2018-01-21T09:00:00.000Z"))
    }
}

internal fun <T> getValue(liveData: LiveData<T>): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(t: T?) {
            data = t
            latch.countDown()
            liveData.removeObserver(this)
        }
    }
    liveData.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)
    return data!!
}
