package com.natiqhaciyef.android_safe_mode_util.util

import com.google.gson.Gson
import com.natiqhaciyef.android_safe_mode_util.constants.COLON
import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_STRING
import com.natiqhaciyef.android_safe_mode_util.constants.HASH
import kotlin.reflect.KClass


fun <T> Map<T, T>.toSQLiteString(): String {
    val keys = this.keys
    var str = EMPTY_STRING

    for (key in keys) {
        str += key
        str += COLON
        str += this[key]
        str += HASH
    }
    return str
}


fun String.toSQLiteMutableMap(): MutableMap<String, String> {
    val map = mutableMapOf<String, String>()
    val list = mutableListOf<String>()
    var vanishData = EMPTY_STRING
    var value = EMPTY_STRING
    var key = EMPTY_STRING


    for (char in this) {
        if (char != '#')
            vanishData += char
        else {
            list.add(vanishData)
            vanishData = EMPTY_STRING
        }
    }

    for (vd in list) {
        for (char in vd) {
            if (char != ':')
                value += char
            else {
                key = value
                value = EMPTY_STRING
            }
        }
        map[key] = value
        key = EMPTY_STRING
        value = EMPTY_STRING
    }
    return map
}


fun <T> List<T>.toSQLiteString(): String {
    var str = EMPTY_STRING
    for (element in this) {
        str += element
        str += HASH
    }
    return str
}


fun String.toSQLiteList(): List<String> {
    val list = mutableListOf<String>()
    var word = EMPTY_STRING
    for (element in this) {
        if (element != '#')
            word += element
        else {
            list.add(word)
            word = EMPTY_STRING
        }
    }

    return list
}

fun <T: Any> String.toSQLiteList(classType: KClass<T>): List<T> {
    val list = mutableListOf<T>()
    var word = EMPTY_STRING

    for (element in this) {
        if (element != '#')
            word += element
        else {
            val fromJson = Gson().fromJson(word, classType.java)
            list.add(fromJson)
            word = EMPTY_STRING
        }
    }

    return list
}


fun <T : Any> String.toSQLiteTypedList(classType: KClass<T>): List<T> {
    val list = mutableListOf<T>()
    var word = EMPTY_STRING

    for (element in this) {
        if (element != '#')
            word += element
        else {
            val fromJson = Gson().fromJson(word, classType.java)
            list.add(fromJson)
            word = EMPTY_STRING
        }
    }

    return list
}

fun String.toSQLiteMutableListOfDouble(): MutableList<Double> {
    val list = mutableListOf<Double>()
    var word = EMPTY_STRING
    for (element in this) {
        if (element != '#')
            word += element
        else {
            list.add(word.toDouble())
            word = EMPTY_STRING
        }
    }

    return list
}
