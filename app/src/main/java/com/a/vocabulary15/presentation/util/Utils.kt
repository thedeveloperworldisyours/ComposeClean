package com.a.vocabulary15.presentation.util

import androidx.compose.ui.graphics.Color
import com.a.vocabulary15.domain.model.Group
import java.util.*

fun convertMillisecondsToCalendar(calendar: Calendar, timeStamp: Long): String {
    return if (timeStamp != 0L) {
        calendar.timeInMillis = timeStamp
        addZeroBefore(calendar, Calendar.DAY_OF_MONTH) +
                addZeroBefore(calendar, Calendar.MONTH) +
                "/${calendar.get(Calendar.YEAR)}\n" +
                addZeroBefore(calendar, Calendar.HOUR_OF_DAY) +
                ":" + addZeroBefore(calendar, Calendar.MINUTE)
    } else {
        ""
    }
}

fun addZeroBefore(calendar: Calendar, field: Int) =
    if (field == Calendar.MONTH){
        if (calendar.get(field) < 10) {
            "/0${calendar.get(Calendar.MONTH) + 1}"
        } else {
            "/${calendar.get(Calendar.MONTH) + 1}"
        }
    } else {
        if (calendar.get(field) < 10) {
            "0${calendar.get(field)}"
        } else {
            "${calendar.get(field)}"
        }
    }


fun findFinalScoreColor(score: Int) = when {
    0 < score -> {
        Color(0xFF51983C)
    }
    0 == score -> {
        Color.Gray
    }
    else -> {
        Color.Red
    }
}

fun getTitleWithID(groups: List<Group>, id: Int): String {
    for (group in groups) {
        if (id == group.id) {
            return group.name
        }
    }
    return ""
}
