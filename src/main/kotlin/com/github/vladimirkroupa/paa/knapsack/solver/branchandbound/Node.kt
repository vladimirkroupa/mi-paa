package com.github.vladimirkroupa.paa.knapsack.solver.branchandbound

import com.github.vladimirkroupa.paa.knapsack.Item
import com.github.vladimirkroupa.paa.knapsack.Knapsack

class Node(private val nodeNumber: Int,
           internal val knapsack: Knapsack,
           private val boundingFunction: BoundingFunction,
           private val excludedItems: Set<Item> = setOf()) {

    fun include(item: Item) = Node(
            this.nodeNumber + 1,
            Knapsack(this.knapsack.add(item)),
            boundingFunction,
            this.excludedItems)

    fun exclude(item: Item) = Node(
            this.nodeNumber + 1,
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
        return (nodeNumber == (other as Node).nodeNumber)
    }

    override fun hashCode(): Int
            = nodeNumber

}