package com.natiqhaciyef.android_safe_mode_util.util

import android.net.Uri
import com.google.gson.Gson

fun <T> jsonMakerWithGson(model: T): String{
    return Gson().toJson(model)
}
inline fun <reified T> jsonReaderFromGson(str: String): T{
    return Gson().fromJson(str, T::class.java)
}

fun <T> jsonEncoderToUri(model: T): String {
    return Uri.encode(jsonMakerWithGson(model))
}