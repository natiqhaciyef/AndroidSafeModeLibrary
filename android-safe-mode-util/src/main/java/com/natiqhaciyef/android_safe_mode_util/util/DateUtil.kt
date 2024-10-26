package com.natiqhaciyef.android_safe_mode_util.util

import android.annotation.SuppressLint
import com.natiqhaciyef.android_safe_mode_util.constants.ELEVEN
import com.natiqhaciyef.android_safe_mode_util.constants.FIFTEEN
import com.natiqhaciyef.android_safe_mode_util.constants.FIVE
import com.natiqhaciyef.android_safe_mode_util.constants.FORMATTED_DATE
import com.natiqhaciyef.android_safe_mode_util.constants.FORMATTED_DATE_TIME
import com.natiqhaciyef.android_safe_mode_util.constants.FOUR
import com.natiqhaciyef.android_safe_mode_util.constants.MINUS
import com.natiqhaciyef.android_safe_mode_util.constants.NINE
import com.natiqhaciyef.android_safe_mode_util.constants.ONE
import com.natiqhaciyef.android_safe_mode_util.constants.SIX
import com.natiqhaciyef.android_safe_mode_util.constants.SIXTEEN
import com.natiqhaciyef.android_safe_mode_util.constants.SIXTY
import com.natiqhaciyef.android_safe_mode_util.constants.SPACE
import com.natiqhaciyef.android_safe_mode_util.constants.TEN
import com.natiqhaciyef.android_safe_mode_util.constants.THIRTY_ONE
import com.natiqhaciyef.android_safe_mode_util.constants.THREE
import com.natiqhaciyef.android_safe_mode_util.constants.TWELVE
import com.natiqhaciyef.android_safe_mode_util.constants.TWENTY_THREE
import com.natiqhaciyef.android_safe_mode_util.constants.TWO
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO
import com.natiqhaciyef.android_safe_mode_util.models.enums.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


fun fromDateToDay(day: String): Int = if (day.startsWith("$ZERO")) ONE else ZERO


fun fromDoubleToTimeLine(d: Double = 7.5): String? {
    return if (d > ZERO.toDouble()) {
        val hours = d.toInt()
        val minutes = ((d % hours) * SIXTY).toInt()
        "$hours hours\n$minutes minutes"
    } else {
        null
    }
}


fun monthToString(month: String) = when (month) {
    "01" -> {
        "January"
    }

    "02" -> {
        "February"
    }

    "03" -> {
        "March"
    }

    "04" -> {
        "April"
    }

    "05" -> {
        "May"
    }

    "06" -> {
        "June"
    }

    "07" -> {
        "July"
    }

    "08" -> {
        "August"
    }

    "09" -> {
        "September"
    }

    "10" -> {
        "October"
    }

    "11" -> {
        "November"
    }

    "12" -> {
        "December"
    }

    else -> "Time left"
}


fun String.toDateChanger(): String? {
    if (this.length != SIXTEEN)
        return null

    if (this.any { it.isLetter() })
        return null

    val subDay = this.substring(ZERO..ONE)
    val subMonth = this.substring(THREE..FOUR)
    val subYear = this.substring(SIX..NINE)
    val subTime = this.substring(ELEVEN..FIFTEEN)

    if (subDay.toInt() > THIRTY_ONE || subMonth.toInt() > TWELVE || subTime.substring(ZERO..ONE)
            .toInt() > TWENTY_THREE || subTime.substring(THREE..FOUR).toInt() > SIXTY
    )
        return null


    if (fromStringToFormattedTime(subTime) == null)
        return null

    val day = if (subDay.startsWith("$ZERO")) subDay[ONE] else subDay
    val month = monthToString(subMonth)
    val time = fromStringToFormattedTime(subTime)

    return "$day $month, $subYear ($time)"
}

fun fromStringToFormattedTime(time: String): String? {
    if (time.length != FIVE)
        return null

    val start = time.substring(ZERO..ONE)

    return if (start.toInt() > TWELVE) {
        "${start.toInt() - TWELVE}:${time.substring(THREE..FOUR)} PM"
    } else {
        if (time.startsWith("$ZERO")) "${time.substring(ONE until time.length)} AM" else "$time AM"
    }
}

fun fromStringTodDay(date: String): String? {
    if (date.length != FIVE)
        return null

    val day = date.substring(ZERO..ONE)
    val month = date.substring(THREE..FOUR)

    if (day.toInt() > THIRTY_ONE || month.toInt() > TWELVE)
        return null

    return "$day ${monthToString(month)}"
}

fun String.toYearMonth(): String? {
    if (this.length < NINE)
        return null

    val month = this.substring(THREE..FOUR)
    val year = this.substring(SIX..NINE)

    if (month.toInt() > TWELVE)
        return null

    return "${monthToString(month).substring(ZERO..TWO)} $year"
}

fun String.toMonthDayHours(): String? {
    if (this.length < 15)
        return null

    val time = this.substring(ELEVEN..FIFTEEN)
    val day = this.substring(ZERO..ONE)
    val month = this.substring(THREE..FOUR)

    if (month.toInt() > TWELVE)
        return null

    return "$day ${monthToString(month).substring(ZERO..TWO)} ($time)"
}

fun String.toMonthDayYearHoursMap(): HashMap<String, String>? {
    if (this.length < 15)
        return null

    val time = this.substring(ELEVEN..FIFTEEN)
    val day = this.substring(ZERO..ONE)
    var month = this.substring(THREE..FOUR)
    val year = this.substring(SIX..NINE)

    val hashMap = hashMapOf(
        Time.DAY.title to day,
        Time.MONTH.title to month,
        Time.YEAR.title to year,
        Time.HOUR.title to time,
    )

    if (month.toInt() > TWELVE)
        return null

    month = monthToString(month)
    hashMap[Time.MONTH.title] = month

    return hashMap
}


@SuppressLint("NewApi")
fun getNow(dateTime: LocalDateTime = LocalDateTime.now()): String {
    val formatter = DateTimeFormatter.ofPattern(FORMATTED_DATE_TIME)
    return formatter.format(dateTime)
}

@SuppressLint("NewApi")
fun getNowDate(dateTime: LocalDateTime = LocalDateTime.now()): String {
    val formatter = DateTimeFormatter.ofPattern(FORMATTED_DATE)
    return formatter.format(dateTime)
}

@SuppressLint("NewApi")
fun String.stringToFormattedLocalDateTime(): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(FORMATTED_DATE_TIME)
    return LocalDateTime.parse(this, formatter)
}

fun dateCalculation(startDate: String, endDateNullable: String?): Int {
    val endDate = endDateNullable ?: getNow()

    val startMonth = startDate.substring(THREE..FOUR).toInt()
    val endMonth = endDate.substring(THREE..FOUR).toInt()

    val startYear = startDate.substring(SIX..NINE).toInt()
    val endYear = endDate.substring(SIX..NINE).toInt()

    return if (startYear == endYear)
        if (startMonth > endMonth)
            ONE
        else {
            val calc = endMonth - startMonth
            if (calc == ZERO)
                ONE
            else
                calc
        }
    else if (startYear < endYear)
        if (startMonth < endMonth)
            endMonth - startMonth + TWELVE * (endYear - startYear)
        else
            TWELVE - startMonth + endMonth + TWELVE * (endYear - startYear - ONE)
    else
        ONE
}

fun calculatedDateConverter(number: Int): String {
    val years = number / 12
    val months = number % 12

    return when {
        years > 1 && months == 0 -> "$years years"
        years == 1 && months == 0 -> "$years year"
        years > 1 && months == 1 -> "$years years $months month"
        years > 1 -> "$years years $months months"
        years == 1 && months > 1 -> "$years year $months months"
        years == 1 -> "$years year $months month"
        months == 1 -> "$months month"
        else -> "$months months"
    }
}


fun userDetailsItemCleaner(startDate: String, endDate: String?, current: String): String {
    val calculate = dateCalculation(startDate, endDate)
    val dateOne = startDate.toYearMonth()
    val dateTwo = endDate?.toYearMonth() ?: current
    return dateOne + SPACE + MINUS + SPACE + dateTwo + " (${calculatedDateConverter(calculate)})"
}
