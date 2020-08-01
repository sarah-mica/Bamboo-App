package com.android.sarahmica.bamboo.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.android.sarahmica.bamboo.GREEN_DATA_FILENAME
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.coroutineScope
import okio.BufferedSource
import okio.Okio

class SproutDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    /*override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(GREEN_DATA_FILENAME).use { inputStream ->
                JsonReader.of(inputStream.reader()).use { jsonReader ->
                    val plantType = object : TypeToken<List<Activity>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(jsonReader, plantType)

                    val database = BambooDatabase.getInstance(applicationContext)
                    database.bambooDatabaseDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error sprouting database", ex)
            Result.failure()
        }
    }*/

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // open the json file where we store all the default Activity data to add to the DB
            applicationContext.assets.open(GREEN_DATA_FILENAME).use { inputStream ->

                /** Convert the [InputStream] to a [BufferedSource] so that moshi JsonReader can use it **/
                val bufferedSource: BufferedSource = Okio.buffer(Okio.source(inputStream))

                JsonReader.of(bufferedSource).use {jsonReader ->

                    // create a jsonAdapter that converts to a List of Activities
                    val moshi: Moshi = Moshi.Builder().build()
                    val listOfActivitiesType = Types.newParameterizedType(List::class.java, Activity::class.java);
                    val jsonAdapter: JsonAdapter<List<Activity>> = moshi.adapter(listOfActivitiesType)

                    val activityList = jsonAdapter.fromJson(jsonReader)

                    if (activityList != null) {
                        val database = BambooDatabase.getInstance(applicationContext)
                        database.activityDao.insertAllActivities(activityList)
                        Result.success()
                    }
                    else {
                        Log.e(TAG, "Unable to parse json data")
                        Result.failure()
                    }

                }
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error sprouting the database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "SproutDatabaseWorker"
    }
}