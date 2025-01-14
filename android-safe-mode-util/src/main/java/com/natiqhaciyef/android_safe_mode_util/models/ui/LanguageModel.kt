package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LanguageModel(
    val name: String,
    val detailedName: String,
    val image: String? = null,
    val level: String,
    val isSelected: Boolean = false
): Parcelable
