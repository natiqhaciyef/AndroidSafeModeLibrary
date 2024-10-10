package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CertificateModel(
    var title: String,
    var date: String,
    var image: String?,
    var academy: String,
    var webLink: String? = null
): Parcelable