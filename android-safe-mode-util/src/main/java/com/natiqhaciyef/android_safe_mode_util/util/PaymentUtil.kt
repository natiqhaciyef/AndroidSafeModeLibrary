package com.natiqhaciyef.android_safe_mode_util.util


import com.natiqhaciyef.android_safe_mode_util.constants.BILLION
import com.natiqhaciyef.android_safe_mode_util.constants.FORMATTED_NUMBER_TWO
import com.natiqhaciyef.android_safe_mode_util.constants.MILLION
import com.natiqhaciyef.android_safe_mode_util.constants.THOUSAND
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO
import com.natiqhaciyef.android_safe_mode_util.models.enums.Currency


fun createPriceRange(
    priceRange: ClosedFloatingPointRange<Float>,
    inputCurrency: Currency,
    time: String
): String {
    val minWage = priceRange.start.toInt().toString().toPriceDividedByDot()
    val maxWage = priceRange.endInclusive.toInt().toString().toPriceDividedByDot()
    val currency = inputCurrency.sign

    return "$currency$minWage - $currency${maxWage} / ${time.lowercase()}"

}

fun priceValueConverter(input: Float, currency: String): String {
    return when(input.toInt()) {
        in ZERO until THOUSAND -> "${FORMATTED_NUMBER_TWO.format(input)} $currency"
        in THOUSAND until MILLION -> "${FORMATTED_NUMBER_TWO.format(input/ THOUSAND)}K $currency"
        in MILLION until BILLION -> "${FORMATTED_NUMBER_TWO.format(input/ MILLION)}M $currency"
        else -> "${FORMATTED_NUMBER_TWO.format(input/ BILLION)}B $currency"
    }
}