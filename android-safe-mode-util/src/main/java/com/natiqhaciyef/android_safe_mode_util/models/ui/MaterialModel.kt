package com.natiqhaciyef.android_safe_mode_util.models.ui

import android.net.Uri
import android.os.Parcelable
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MaterialModel(
    var id: String,
    var image: String,
    var title: String,
    var description: String?,
    var createdDate: String,
    var type: String,
    var url: Uri,
    var size: Float? = null,
    var sizeUnit: String? = null,
    var downloadedUri: String? = null,
    var isDownloading: Boolean = false
): Parcelable