package com.android.sarahmica.bamboo

import java.util.*

fun calendarDateEquals(dayOne: Calendar, dayTwo: Calendar): Boolean {
    val dayOneDateOnly: Calendar = setCalendarDateOnly(dayOne)
    val dayTwoDateOnly: Calendar = setCalendarDateOnly(dayTwo)

    return dayOneDateOnly == dayTwoDateOnly
}

fun setCalendarDateOnly(date: Calendar): Calendar {
    return date.apply {
        set(Calendar.HOUR, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        set(Calendar.AM_PM, Calendar.AM)
    }
}