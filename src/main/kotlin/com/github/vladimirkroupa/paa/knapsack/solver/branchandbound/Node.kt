package com.github.vladimirkroupa.paa.knapsack.solver.branchandbound

import com.github.vladimirkroupa.paa.knapsack.Item
import com.github.vladimirkroupa.paa.knapsack.Knapsack

class Node(internal val knapsack: Knapsack,
           private val boundingFunction: BoundingFunction,
           val excludedItems: Set<Item> = setOf()) {

    fun include(item: Item) = Node(
            Knapsack(this.knapsack.add(item)),
            boundingFunction,
            this.excludedItems)

    fun exclude(item: Item) = Node(
            Knapsack(this.knapsack),
            boundingFunction,
            this.excludedItems.plus(item))

    val upperBound: Int by lazy { boundingFunction.computeUpperBound(this) }

    fun isOptimalSolution(): Boolean {
        val processedItems = knapsack.itemsInsideCount + excludedItems.size
        val totalItems = knapsack.problemInstance.itemCount
        return processedItems == totalItems
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node

        if (knapsack != other.knapsack) return false
        if (excludedItems != other.excludedItems) return false

        return true
    }

    override fun hashCode(): Int {
        var result = knapsack.hashCode()
        result = 31 * result + excludedItems.hashCode()
        return result
    }

    override fun toString(): String
            = "B: $upperBound, E: ${excludedItems.size}, I: ${knapsack.itemsInsideCount}"

}