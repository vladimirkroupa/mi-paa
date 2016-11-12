package com.github.vladimirkroupa.paa.knapsack

class Item(val itemIndex: Int, private val problemInstance: Problem) {

    val value: Int
        get() = problemInstance.itemValues[itemIndex]

    val weight: Int
        get() = problemInstance.itemWeights[itemIndex]

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false
        return (itemIndex == (other as Item).itemIndex)
    }

    override fun hashCode(): Int
            = itemIndex

    override fun toString(): String
            = "$value V/ $weight W"

}

