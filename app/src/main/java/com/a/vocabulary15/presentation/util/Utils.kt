package com.a.vocabulary15.presentation.util

import java.util.*

fun convertMillisecondsToCalendar(calendar: Calendar, timeStamp: Long): String {
    return if (timeStamp != 0L) {
        calendar.timeInMillis = timeStamp
        "${calendar.get(Calendar.HOUR_OF_DAY)}" +
                ":${calendar.get(Calendar.MINUTE)}" +
                "-${calendar.get(Calendar.DAY_OF_MONTH)}" +
                "/${calendar.get(Calendar.MONTH) + 1}" +
                "/${calendar.get(Calendar.YEAR)}"
    } else {
        ""
    }
}