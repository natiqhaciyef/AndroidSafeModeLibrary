package com.natiqhaciyef.android_safe_mode_util.models.io

data class SafeNetworkResult<T: Any>(
    val result: CrudModel,
    val data: T?
)

fun <T: Any> SafeNetworkResult<T>.handleResponse(): T?{
    if (data == null || result.resultCode !in 200..299){
        return null
    }

    return data
}