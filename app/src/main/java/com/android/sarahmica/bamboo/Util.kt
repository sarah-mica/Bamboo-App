package com.android.sarahmica.bamboo

fun ActivityTypeStringToEnum(activityString: String): ActivityType {
    return when (activityString) {
        "waste" -> ActivityType.WASTE
        "energy" -> ActivityType.ENERGY
        "water" -> ActivityType.WATER
        "activism" -> ActivityType.ACTIVISM
        else -> {
            ActivityType.UNKNOWN
        }
    }
}