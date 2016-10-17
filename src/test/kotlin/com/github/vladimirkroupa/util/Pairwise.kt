package com.github.vladimirkroupa.util

fun <T> Collection<T>.pairwise(): Pair<List<T>, List<T>> {
    if (size % 2 != 0) {
        throw IllegalArgumentException("Collection of odd length cannot be split int two.")
    }

    val list1 = mutableListOf<T>()
    val list2 = mutableListOf<T>()
    val it = this.iterator()
    while (it.hasNext()) {
        list1.add(it.next())
        list2.add(it.next())
    }
    return Pair(list1, list2)
}