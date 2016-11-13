package com.github.vladimirkroupa.paa.knapsack.solver.branchandbound

import com.github.vladimirkroupa.paa.knapsack.Item
import java.util.*

class ItemFractionsBoundingFunction(private val itemsByValueWeightRatio: List<Item>) : BoundingFunction {

    override fun computeUpperBound(node: Node): Int {
        val knapsack = node.knapsack
        if (knapsack.totalWeight > knapsack.capacity) {
            return Int.MIN_VALUE
        }

        var remainingCapacity = knapsack.remainingCapacity
        var possibleItemsValue = 0.0
        for (item in itemsByValueWeightRatio) {
            if (knapsack.contains(item)) {
                continue
            }
            if (item.weight <= remainingCapacity) {
                possibleItemsValue += item.value
                remainingCapacity -= item.weight
            } else {
                val itemFraction = remainingCapacity.toDouble() / item.weight
                possibleItemsValue += item.value * itemFraction
                remainingCapacity = 0
            }

        }
        val roundedPossibleItemsValue = Math.floor(possibleItemsValue).toInt()
        val includedItemsValue = knapsack.totalValue
        return includedItemsValue + roundedPossibleItemsValue
    }

}