package com.natiqhaciyef.android_safe_mode_util.base.ui

import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties


interface UiState {
    var isLoading: Boolean
    var isError: Boolean

    fun <T : UiState> isPropertiesNull(obj: T): Boolean {
        val properties = obj::class.memberProperties.toMutableList()
        val values = mutableMapOf<String, Any?>()
        for (property in properties) {
            if (property.name != "isLoading" && property.name != "isError") {
                val value = (property as? KProperty1<T, *>)?.get(obj)
                values[property.name] = value
            }
        }

        return values.values.all { it == null }
    }
}

interface UiEvent
interface UiEffect