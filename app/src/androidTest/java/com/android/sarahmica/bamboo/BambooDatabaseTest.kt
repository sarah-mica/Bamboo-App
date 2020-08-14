package com.android.sarahmica.bamboo

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.sarahmica.bamboo.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.io.IOException
import java.util.*
import java.util.Calendar.*

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
    fun insertAndGetEntry() = runBlocking {

        // first insert a new log entry
        var logEntry: LogEntry? = LogEntry()
        logEntryDao.insert(logEntry!!)

        assert(logEntry.id != 0L)
        Timber.i("logEntry id: " + logEntry.id)

        val dayStart: Calendar = Calendar.getInstance().apply {
            set(HOUR, 0)
            set(MINUTE, 0)
            set(SECOND, 0)
            set(MILLISECOND, 0)
            set(AM_PM, AM)
        }
        val dayEnd: Calendar = Calendar.getInstance().apply {
            set(HOUR, 11)
            set(MINUTE, 59)
            set(SECOND, 59)
            set(MILLISECOND, 999)
            set(AM_PM, PM)
        }

        logEntry = logEntryDao.getEntryForDay(dayStart, dayEnd)
        assert(logEntry != null)
        Timber.i("logEntry id: " + logEntry!!.id)

        // Now insert a logEntry with all its associated activities
        val logEntryWithActivities = ActivityLogEntry(1, logEntry.id)
        logEntryWithActivityDao.insert(logEntryWithActivities)

        val retrievedEntryWithActivities = logEntryWithActivityDao.getLogEntryWithActivities(dayStart, dayEnd)
        assert(retrievedEntryWithActivities != null)

        assertEquals("log entry ids do not match", logEntry.id, retrievedEntryWithActivities!!.logEntry.id)
        assert(retrievedEntryWithActivities.greenActivityList != null)
        assertEquals("activity id inserted does not match", 1,
            retrievedEntryWithActivities.greenActivityList.get(0).activityId
        )
    }
}