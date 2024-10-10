package com.natiqhaciyef.android_safe_mode_util.models.enums

object FileTypes {
    const val ALL_FILES = "*/*"
    const val URL = "url"
    const val PDF = "PDF"
    const val DOCX = "DOCX"
    const val PNG = "PNG"
    const val JPEG = "JPEG"
    const val MP4 = "MP4"
    const val XLS = "XLS"

    fun getIntentFileType(type: String) = when (type) {
        URL -> "text/plain"
        PDF -> "application/pdf"
        DOCX -> "application/docx"
        PNG -> "image/png"
        JPEG -> "image/jpeg"
        MP4 -> "video/mp4"
        else -> "*/*"
    }

}