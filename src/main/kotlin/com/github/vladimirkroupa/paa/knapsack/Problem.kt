package com.github.vladimirkroupa.paa.knapsack

class Problem(val knapsackCapacity: Int, val itemWeights: List<Int>, val itemValues: List<Int>) {

    val items: Array<Item>

    init {
        if (itemWeights.size != itemValues.size) {
            val msg = "List of weights and list of values must be of same length. ${itemWeights.size} != ${itemValues.size}"
            throw IllegalArgumentException(msg)
        }
        val itemCount = itemWeights.size
        items = Array(itemCount, { itemIx -> Item(itemIx, this) })
    }

    val itemCount: Int
        get() = items.size

}