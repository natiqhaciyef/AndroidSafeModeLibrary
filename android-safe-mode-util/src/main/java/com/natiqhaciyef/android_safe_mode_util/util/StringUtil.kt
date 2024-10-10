package com.natiqhaciyef.android_safe_mode_util.util

import com.natiqhaciyef.android_safe_mode_util.constants.DOT
import com.natiqhaciyef.android_safe_mode_util.constants.EMPTY_STRING
import com.natiqhaciyef.android_safe_mode_util.constants.ONE
import com.natiqhaciyef.android_safe_mode_util.constants.PLUS
import com.natiqhaciyef.android_safe_mode_util.constants.SPACE
import com.natiqhaciyef.android_safe_mode_util.constants.STAR
import com.natiqhaciyef.android_safe_mode_util.constants.TEN
import com.natiqhaciyef.android_safe_mode_util.constants.TWO
import com.natiqhaciyef.android_safe_mode_util.constants.ZERO


fun String.secondWordFirstLetterLowercase(): String {
    val secondWord = this.findSecondWord().first.lowercaseFirstLetter()
    val secondWordFirstIndex = this.findSecondWord().second

    val modified = this.replaceRange(
        secondWordFirstIndex until secondWordFirstIndex + secondWord.length,
        secondWord
    )
    return modified
}

fun String.findSecondWord(): Pair<String, Int> {
    var isSecond = false
    var secondWord = EMPTY_STRING
    var indexOfFirstLetter = ZERO

    for (i in this.indices) {

        if (isSecond) {
            if (indexOfFirstLetter > i || indexOfFirstLetter == ZERO)
                indexOfFirstLetter = i
            secondWord += this[i]
        }

        if (this[i].toString() == EMPTY_STRING || this[i].toString() == SPACE) {
            isSecond = !isSecond

            if (!isSecond) {
                return Pair(secondWord.trim(), indexOfFirstLetter)
            }
        }
    }

    return Pair(secondWord.trim(), indexOfFirstLetter)
}

fun String.capitalizeFirstLetter(): String {
    val firstLetter = this[ZERO].toString().uppercase()[ZERO]
    return this.replaceFirst(this[ZERO], firstLetter)
}

fun String.lowercaseFirstLetter(): String {
    val firstLetter = this[ZERO].toString().lowercase()[ZERO]
    return this.replaceFirst(this[ZERO], firstLetter)
}

fun String.toMaskedPhoneNumber(prefix: String = "994"): String {
    var result = PLUS

    for (i in this.indices) {
        if (prefix.length <= i && i != this.lastIndex)
            result += STAR
        else
            result += this[i]
    }

    return result
}

fun String.toMaskedEmail(): String {
    var result = EMPTY_STRING

    for (i in this.indices) {
        if (TWO <= i && i + TEN < this.lastIndex)
            result += STAR
        else
            result += this[i]
    }

    return result
}

fun String.toPriceDividedByDot(): String {
    var result = EMPTY_STRING
    var counter = ONE

    for (element in this.reversed()) {
        if (counter == 3) {
            result += element
            result += DOT
            counter = ZERO
        } else
            result += element

        counter += ONE
    }

    result = result.removePrefix(DOT)
    result = result.removeSuffix(DOT)

    return result.reversed()
}
