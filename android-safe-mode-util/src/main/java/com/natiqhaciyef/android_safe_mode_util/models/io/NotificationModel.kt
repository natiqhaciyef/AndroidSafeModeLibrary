package com.natiqhaciyef.android_safe_mode_util.models.io

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.natiqhaciyef.android_safe_mode_util.models.enums.NotificationType
import kotlinx.parcelize.Parcelize

abstract class Notification{
    abstract var isViewed: Boolean
    abstract val deeplink: String?
    abstract val description: List<String>
    abstract val type: NotificationType
}

@Parcelize
data class NotificationModel(
    val title: String,
    val image: String,
    override var isViewed: Boolean,
    override val deeplink: String? = null,
    override val description: List<String> = listOf(),
    val date: String,
    override val type: NotificationType,
): Notification(), Parcelable

@Parcelize
data class NotificationSenderModel(
    override var isViewed: Boolean = false,
    override val deeplink: String? = null,
    val title: String,
    @DrawableRes val image: Int,
    override val description: List<String> = listOf(),
    override val type: NotificationType,
): Notification(), Parcelable
