package com.natiqhaciyef.android_safe_mode_util.models.enums

enum class Regions {
    ASIA,
    AFRICA,
    NORTH_AMERICA,
    SOUTH_AMERICA,
    ASIA_EUROPE,
    EUROPE,
    OCEANIA,
    NOT_SELECTED;

    companion object {
        fun stringToTypeConvert(region: String): Regions = when (region) {
            ASIA.name, ASIA.name.lowercase() -> ASIA
            AFRICA.name, AFRICA.name.lowercase() -> AFRICA
            ASIA_EUROPE.name, ASIA_EUROPE.name.lowercase() -> ASIA_EUROPE
            NORTH_AMERICA.name, NORTH_AMERICA.name.lowercase() -> NORTH_AMERICA
            SOUTH_AMERICA.name, SOUTH_AMERICA.name.lowercase() -> SOUTH_AMERICA
            EUROPE.name, EUROPE.name.lowercase() -> EUROPE
            OCEANIA.name, OCEANIA.name.lowercase() -> OCEANIA
            else -> NOT_SELECTED
        }
    }
}