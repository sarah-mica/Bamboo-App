package com.android.sarahmica.bamboo.database

import android.content.Context
import android.content.res.ColorStateList
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.sarahmica.bamboo.ActivityType
import com.android.sarahmica.bamboo.R
import com.squareup.moshi.JsonClass
import timber.log.Timber

@Entity(tableName = "green_activities")
@JsonClass(generateAdapter = true)
data class GreenActivity (

    @PrimaryKey
    val activityId: Int,

    // What is the name of the activity that we'll display to the user?
    @ColumnInfo(name = "activity_name")
    val activityName: String,

    // What group of activities does this belong to?
    @ColumnInfo(name = "activity_type")
    val activityType: ActivityType,

    // How much many "panda points" is this activity worth?
    @ColumnInfo(name = "point_value")
    val pointValue: Int

) {

    @Suppress("DEPRECATION")
    fun getChipColor(context: Context): ColorStateList? {
        return when(this.activityType) {
            ActivityType.WASTE -> context.resources.getColorStateList(R.color.waste_chip_color_state_list)
            ActivityType.WASTE -> context.resources.getColorStateList(R.color.waste_chip_color_state_list)
            ActivityType.ENERGY -> context.resources.getColorStateList(R.color.energy_chip_color_state_list)
            ActivityType.WATER -> context.resources.getColorStateList(R.color.water_chip_color_state_list)
            ActivityType.ACTIVISM -> context.resources.getColorStateList(R.color.activism_chip_color_state_list)
            else -> {
                Timber.e("Unknown ActivityType!")
                context.resources.getColorStateList(R.color.activism_chip_color_state_list)
            }
        }
    }
}