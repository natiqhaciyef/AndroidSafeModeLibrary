package com.natiqhaciyef.android_safe_mode_util.models.enums

enum class EducationDegrees(val title: String) {
    NOT_SELECTED("Not selected"),
    HIGH_SCHOOL("High School"),
    BACHELOR("Bachelor"),
    MASTER("Master"),
    PHD("PhD");

    companion object {
        fun stringToUniversityDegree(title: String) = when (title.lowercase()) {
            HIGH_SCHOOL.name.lowercase(), HIGH_SCHOOL.title.lowercase() -> HIGH_SCHOOL
            BACHELOR.name.lowercase(), BACHELOR.title.lowercase() -> BACHELOR
            MASTER.name.lowercase(), MASTER.title.lowercase() -> MASTER
            PHD.name.lowercase(), PHD.title.lowercase() -> PHD
            else -> NOT_SELECTED
        }

        fun createList() = listOf(
            HIGH_SCHOOL,
            BACHELOR,
            MASTER,
            PHD,
        )
    }
}