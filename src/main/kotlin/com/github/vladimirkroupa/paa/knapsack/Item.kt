package com.github.vladimirkroupa.paa.knapsack

class Item(val itemIndex: Int, private val problemInstance: Problem) {

    val value: Int
        get() = problemInstance.itemValues[itemIndex]

    val weight: Int
        get() = problemInstance.itemWeights[itemIndex]

    override fun toString(): String {
        return "$value V/ $weight W"
    }

}

