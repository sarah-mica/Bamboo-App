package com.android.sarahmica.bamboo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.android.sarahmica.bamboo.DATABASE_NAME

/**
 * A database that stores [LogEntry] information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [LogEntry::class, GreenActivity::class], version = 2, exportSchema = false)
abstract class BambooDatabase : RoomDatabase(){

    /**
     * Connects the database to the DAO
     */
    abstract fun bambooDatabaseDao(): LogEntryDao

    abstract fun activityDao(): GreenActivityDao

    /**
     * Define a companion object, this allows us to add functions on the BambooDatabase class.
     *
     * For example, clients can call `BambooDatabase.getInstance(context)` to instantiate
     * a new BambooDatabase.
     */
    companion object {

        /**
         * INSTANCE will keep a reference to any database returned via getInstance.
         *
         * This will help us avoid repeatedly initializing the database, which is expensive.
         *
         *  The value of a volatile variable will never be cached, and all writes and
         *  reads will be done to and from the main memory. It means that changes made by one
         *  thread to shared data are visible to other threads.
         */
        @Volatile private var instance: BambooDatabase? = null

        fun getInstance(context: Context): BambooDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database
        private fun buildDatabase(context: Context): BambooDatabase {
            return Room.databaseBuilder(context, BambooDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SproutDatabaseWorker>().build()
                        WorkManager.getInstance().enqueue(request)
                    }
                })
                .build()
        }

    }
}