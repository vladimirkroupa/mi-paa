package com.github.vladimirkroupa.paa.knapsack

class BranchAndBoundSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    var best: Knapsack = Knapsack(problemInstance)
    var incumbent: Int = Int.MIN_VALUE

    override fun solve(): Knapsack {
        val knapsack = Knapsack(problemInstance)
        doSolve(knapsack)
        return best
    }

    private fun doSolve(knapsack: Knapsack) {
        //println(knapsack.printStep())
        knapsack.remainingItems.forEach { item ->
            if (knapsack.hasRoomFor(item)) {
                val knapsackWithItem = knapsack.add(item)

                val upperBound = computeUpperBound(knapsackWithItem)
                if (upperBound > incumbent) {
                    incumbent = knapsackWithItem.totalValue
                    best = knapsackWithItem
                    doSolve(knapsackWithItem)
                }
            }
        }
    }

    private fun computeUpperBound(knapsack: Knapsack): Int {
        val remaningItemsValue = knapsack.getRemainingItemsFrom(knapsack.lastItemIndex).fold(0, { acc, item -> acc + item.value })
        return knapsack.totalValue + remaningItemsValue
    }

}