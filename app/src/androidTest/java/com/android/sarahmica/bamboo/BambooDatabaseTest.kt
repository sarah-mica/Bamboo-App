package com.android.sarahmica.bamboo

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.sarahmica.bamboo.database.*
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BambooDatabaseTest {

    private lateinit var logEntryDao: LogEntryDao
    private lateinit var greenActivityDao: GreenActivityDao
    private lateinit var logEntryWithActivityDao: LogEntryWithActivityDao
    private lateinit var db: BambooDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears
        // when the process is killed
        db = Room.inMemoryDatabaseBuilder(context, BambooDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        logEntryDao = db.logEntryDao()
        greenActivityDao = db.activityDao()
        logEntryWithActivityDao = db.logEntryWithActivitiesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun insertAndGetEntry() {
        val logEntry = LogEntry()
        logEntryDao.insert(logEntry)
        assert(logEntry.id != 0L)
        Timber.i("logEntry id: " + logEntry.id)
        logEntry = logEntryDao.get()
        val logEntryWithActivities = ActivityLogEntry(1, logEntry.id)
        logEntryWithActivityDao.insert(logEntryWithActivities)
        val retrievedEntryWithActivities = logEntryWithActivityDao.getLogEntriesWithActivities()

        assertEquals("log entry ids do not match", logEntry.id, retrievedEntryWithActivities.value?.get(0)?.logEntry?.id)
        assertEquals("activity id inserted does not match", 1,
            retrievedEntryWithActivities.value?.get(0)?.greenActivityList?.get(0)?.activityId
        )
    }
}