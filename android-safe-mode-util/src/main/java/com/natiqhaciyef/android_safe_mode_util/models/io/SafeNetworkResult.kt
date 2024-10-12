package com.natiqhaciyef.android_safe_mode_util.models.io

data class SafeNetworkResult<T: Any>(
    val result: CrudModel,
    val data: T
)