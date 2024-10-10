@file:OptIn(ExperimentalStdlibApi::class)

package com.natiqhaciyef.android_safe_mode_util.util



private const val PROPERTY_CHECK_LOG = "PROPERTY_CHECK_LOG => "

/**
 * This function created for specify is given list sublist of parent.
 * @param parentList is the parent list which has more data in.
 *
 * @author Natig Hajiyev
 * */
fun <T> List<T>.isSubListOf(parentList: List<T>): Boolean {
    val listOfTruth = mutableListOf<Boolean>()

    for (element in this) {
        if (parentList.contains(element))
            listOfTruth.add(true)
    }

    return listOfTruth.size == this.size
}

/**
 * This function created for finding median of list. There are 2 condition that's why return type
 * is Pair class.
 * @return Pair (if list size is odd then both values of pair will same, else if number is even then
 * two number on center will return as Pair)
 *
 * @author Natig Hajiyev
 * */
fun <T> List<T>.findMedian(): Pair<T, T> {
    return if (this.size % 2 == 1) {
        val indexOfCenter = (this.size - 1) / 2
        val center = this[indexOfCenter]

        Pair(center, center)
    }else {
        val indexOfCenter = this.size / 2
        val center1 = this[indexOfCenter]
        val center2 = this[indexOfCenter + 1]

        Pair(center1, center2)
    }
}


//fun List<T>.
fun main() {
    val list = mutableListOf("Empty", "Filled", "Data")
}


