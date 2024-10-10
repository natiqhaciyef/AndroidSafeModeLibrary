package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OtpMethod(
    val type: String,
    val data: String
): Parcelable
