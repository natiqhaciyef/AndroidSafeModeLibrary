package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProjectModel (
    var appName: String,
    var position: String,
    var startDate: String,
    var endDate: String?,
    var webLink: String?
): Parcelable