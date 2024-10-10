package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UniversityModel(
    val logo: String,
    val name: String,
    val country: String,
): Parcelable