package com.natiqhaciyef.android_safe_mode_util.models.enums

enum class Currency(var sign: String) {
    AZN("₼"),
    USD("$"),
    EUR("€"),
    CAD("C$"),
    TL("₺"),
    RUB("₽"),
    POUND("￡"),
    DEFAULT("-");

    companion object{
        fun stringToType(str: String): Currency {
            return when(str.lowercase()){
                AZN.name.lowercase() -> { AZN }
                USD.name.lowercase() -> { USD }
                EUR.name.lowercase() -> { EUR }
                CAD.name.lowercase() -> { CAD }
                TL.name.lowercase() -> { TL }
                POUND.name.lowercase() -> { POUND }
                RUB.name.lowercase() -> { RUB }
                else -> DEFAULT
            }
        }

        fun stringToSign(str: String): String{
            return when(str.lowercase()){
                AZN.name.lowercase() -> { AZN.sign }
                USD.name.lowercase() -> { USD.sign }
                EUR.name.lowercase() -> { EUR.sign }
                CAD.name.lowercase() -> { CAD.sign }
                TL.name.lowercase() -> { TL.sign }
                POUND.name.lowercase() -> { POUND.sign }
                RUB.name.lowercase() -> { RUB.sign }
                else -> DEFAULT.sign
            }
        }
    }
}