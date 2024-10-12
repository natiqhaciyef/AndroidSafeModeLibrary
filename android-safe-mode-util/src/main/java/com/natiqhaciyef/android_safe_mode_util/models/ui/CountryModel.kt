package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_STRING
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO
import com.natiqhaciyef.android_safe_mode_util.models.enums.Regions
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    var id: Int = ZERO,
    val name: String,
    val flag: String = EMPTY_STRING,
    val numberPrefix: String = EMPTY_STRING,
    val capital: String = EMPTY_STRING,
    val currency: String = EMPTY_STRING,
    val region: String = Regions.NOT_SELECTED.name,
    val religion: String = EMPTY_STRING,
    val language: String = EMPTY_STRING
): Parcelable
