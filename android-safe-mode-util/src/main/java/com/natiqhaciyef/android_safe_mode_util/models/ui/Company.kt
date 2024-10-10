package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(
    val title: String,
    val logo: String,
    val about: String
): Parcelable