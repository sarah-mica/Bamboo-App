@file:Suppress("BlockingMethodInNonBlockingContext", "BlockingMethodInNonBlockingContext")

package com.android.sarahmica.bamboo.database

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.sarahmica.bamboo.GREEN_DATA_FILENAME
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.coroutineScope
import okio.BufferedSource
import okio.Okio
import timber.log.Timber

/**
 * Do the work to read all the entries from the supplied JSON file and prepopulate the Activity table
 * in the DB with all the available activities for users to choose from
 */
class SproutDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // open the json file where we store all the default Activity data to add to the DB
            applicationContext.assets.open(GREEN_DATA_FILENAME).use { inputStream ->

                // Convert the InputStream to a BufferedSource so that moshi JsonReader can use it
                val bufferedSource: BufferedSource = Okio.buffer(Okio.source(inputStream))

                JsonReader.of(bufferedSource).use {jsonReader ->

                    // create a jsonAdapter that converts to a List of Activities
                    val moshi: Moshi = Moshi.Builder().build()
                    val listOfActivitiesType = Types.newParameterizedType(List::class.java, GreenActivity::class.java)
                    val jsonAdapter: JsonAdapter<List<GreenActivity>> = moshi.adapter(listOfActivitiesType)

                    val activityList = jsonAdapter.fromJson(jsonReader)

                    if (activityList != null) {
                        val database = BambooDatabase.getInstance(applicationContext)
                        database.activityDao().insertAllActivities(activityList)
                        Result.success()
                    }
                    else {
                        Timber.e("Unable to parse json data")
                        Result.failure()
                    }

                }
            }
        } catch (ex: Exception) {
            Timber.e(ex,  "Error sprouting the database")
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SproutDatabaseWorker"
    }
}