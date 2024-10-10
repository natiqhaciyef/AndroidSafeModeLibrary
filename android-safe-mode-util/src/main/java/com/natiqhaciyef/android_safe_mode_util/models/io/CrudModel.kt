package com.natiqhaciyef.android_safe_mode_util.models.io

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CrudModel(
    val resultCode: Int,
    val message: String,
): Parcelable