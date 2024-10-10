package com.natiqhaciyef.android_safe_mode_util.base.network

import android.os.Parcelable
import com.natiqhaciyef.android_safe_mode_util.models.io.CrudModel


interface BaseNetworkModel: Parcelable {
    var result: CrudModel?
}