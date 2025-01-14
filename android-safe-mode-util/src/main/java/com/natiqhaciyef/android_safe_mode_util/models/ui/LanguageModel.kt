package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_STRING
import kotlinx.parcelize.Parcelize

@Parcelize
data class LanguageModel(
    val tag: String,
    val detailedName: String = EMPTY_STRING,
    val image: String? = null,
    val level: String,
    val isSelected: Boolean = false
): Parcelable
