package com.natiqhaciyef.android_safe_mode_util.models.enums

enum class Genders {
    MALE,
    FEMALE,
    NOT_DEFINED;

    companion object{
        fun stringToType(str: String) = when(str.lowercase()){
            MALE.name.lowercase() -> MALE
            FEMALE.name.lowercase() -> FEMALE
            else -> NOT_DEFINED
        }
    }
}