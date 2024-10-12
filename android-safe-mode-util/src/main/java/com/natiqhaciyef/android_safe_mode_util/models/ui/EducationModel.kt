package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import com.natiqhaciyef.android_safe_mode_util.models.enums.EducationDegrees
import kotlinx.parcelize.Parcelize

@Parcelize
data class EducationModel(
    val field: String,
    val degree: String = EducationDegrees.HIGH_SCHOOL.title,
    val universityName: String,
    val universityIcon: String?,
    val startDate: String,
    val endDate: String,
    val gpa: Float? = null,
): Parcelable