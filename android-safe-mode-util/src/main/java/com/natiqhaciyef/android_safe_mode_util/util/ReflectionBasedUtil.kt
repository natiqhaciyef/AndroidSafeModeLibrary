package com.natiqhaciyef.android_safe_mode_util.util

import com.natiqhaciyef.android_safe_mode_util.models.ui.ProjectModel
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties


/**
 * Main purpose of function is checking given fieldName is nullable marked or not. It may affect
 * developers decision before creating instance of class.
 * @author Natig Hajiyev
 * */

fun <T: Any> KClass<T>.isFieldNullable(fieldName: String): Boolean {
    val properties = this.memberProperties

    for (property in properties) {
        if (property.name.lowercase() == fieldName.lowercase()) {
            return property.returnType.isMarkedNullable
        }
    }

    return false
}



fun main() {
    println(ProjectModel::class.isFieldNullable("webLink"))
}