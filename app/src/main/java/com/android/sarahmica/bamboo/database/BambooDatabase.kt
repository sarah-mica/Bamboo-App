package com.android.sarahmica.bamboo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * A database that stores [GreenActivity] information.
 * And a global method to get access to the database.
 *
 * This pattern is pretty much the same for any database,
 * so you can reuse it.
 */
@Database(entities = [GreenActivity::class], version = 1, exportSchema = false)
abstract class BambooDatabase : RoomDatabase(){

    /**
     * Connects the database to the DAO
     */
    abstract val bambooDatabaseDao: BambooDatabaseDao

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
        @Volatile
        private var INSTANCE: BambooDatabase? = null

        fun getInstance(context: Context): BambooDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BambooDatabase::class.java,
                        "green_activity_history_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // migration with Room in this blog post:
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}