package com.github.vladimirkroupa.paa.knapsack

class HeuristicSolver : KnapsackSolver {

    override fun solve(problemInstance: Problem): Knapsack? {
        val knapsack = Knapsack(problemInstance)
        return doSolve(knapsack)
    }

    private fun doSolve(knapsack: Knapsack): Knapsack {
        //println(knapsack.printStep())

        val itemsByRatio = knapsack.remainingItems.sortedByDescending(Item::valueToWeightRatio)
        val bestFittingItem = itemsByRatio.firstOrNull { item ->
            knapsack.hasRoomFor(item)
        }
        if (bestFittingItem != null) {
            val knapsackWithItem = knapsack.add(bestFittingItem)
            return doSolve(knapsackWithItem)
        } else {
            return knapsack
        }
    }

}

val Item.valueToWeightRatio: Double
    get() = this.value.toDouble() / this.weight.toDouble()