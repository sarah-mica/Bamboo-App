package com.android.sarahmica.bamboo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

enum class ActivityType(val dbVal: Int) {

    UNKNOWN(0),

    @Json(name = "waste")
    WASTE(1),

    @Json(name = "energy")
    ENERGY(2),

    @Json(name = "water")
    WATER(3),

    @Json(name = "activism")
    ACTIVISM(4)

}