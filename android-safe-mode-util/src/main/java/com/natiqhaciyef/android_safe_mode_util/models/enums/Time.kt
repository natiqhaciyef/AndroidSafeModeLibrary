package com.natiqhaciyef.android_safe_mode_util.models.enums

enum class Time(val title: String) {
    SECOND("Second"),
    MINUTE("Minute"),
    HOUR("Hour"),
    DAY("Day"),
    WEEK("Week"),
    MONTH("Month"),
    YEAR("Year"),
    DECADE("Decade"),
    NON_OF_THEM("Non of them");

    companion object{
        fun stringToTimeType(time: String) = when(time.lowercase()){
            SECOND.name, SECOND.name.lowercase() -> { SECOND }
            MINUTE.name, MINUTE.name.lowercase() -> { MINUTE }
            HOUR.name, HOUR.name.lowercase() -> { HOUR }
            DAY.name, DAY.name.lowercase() -> { DAY }
            WEEK.name, WEEK.name.lowercase() -> { WEEK }
            MONTH.name, MONTH.name.lowercase() -> { MONTH }
            YEAR.name, YEAR.name.lowercase() -> { YEAR }
            DECADE.name, DECADE.name.lowercase() -> { DECADE }

            else -> { NON_OF_THEM }
        }

        fun createTimeList() = listOf(SECOND.title, MINUTE.title, HOUR.title, DAY.title, WEEK.title, MONTH.title, YEAR.title)
    }
}