package com.natiqhaciyef.common.models.enums

enum class LanguageLevels(val title: String) {
    ELEMENTARY("Elementary proficiency"),
    LIMITED("Limited working proficiency"),
    PROFESSIONAL("Professional working proficiency"),
    FULL_PROFESSIONAL("Full professional proficiency"),
    NATIVE_BILINGUAL("Native or bilingual proficiency"),
    NON_OF_THEM("Non of them");

    companion object{
        fun createList() = listOf(
            ELEMENTARY.title,
            LIMITED.title,
            PROFESSIONAL.title,
            FULL_PROFESSIONAL.title,
            NATIVE_BILINGUAL.title,
        )

        fun stringToLanguage(title: String) = when(title.lowercase()){
            ELEMENTARY.title.lowercase(), ELEMENTARY.name.lowercase() -> ELEMENTARY
            LIMITED.title.lowercase(), LIMITED.name.lowercase() -> LIMITED
            PROFESSIONAL.title.lowercase(), PROFESSIONAL.name.lowercase() -> PROFESSIONAL
            FULL_PROFESSIONAL.title.lowercase(), FULL_PROFESSIONAL.name.lowercase() -> FULL_PROFESSIONAL
            NATIVE_BILINGUAL.title.lowercase(), NATIVE_BILINGUAL.name.lowercase() -> NATIVE_BILINGUAL
            else -> NON_OF_THEM
        }
    }
}