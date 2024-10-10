package com.natiqhaciyef.android_safe_mode_util.models.io


data class TokenModel(
    var accessToken: String?,
    var refreshToken: String?,
    var result: CrudModel?
)