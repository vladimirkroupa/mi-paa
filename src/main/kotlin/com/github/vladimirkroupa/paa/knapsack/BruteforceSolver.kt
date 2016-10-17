package com.github.vladimirkroupa.paa.knapsack

class BruteforceSolver : KnapsackSolver {

    var best: Knapsack? = null

    override fun solve(problemInstance: Problem): Knapsack? {
        best = null
        val knapsack = Knapsack(problemInstance)
        doSolve(knapsack)
        return best
    }

    private fun doSolve(knapsack: Knapsack) {
        if (best == null || knapsack.totalValue > best!!.totalValue) {
            best = knapsack
        }

        println(knapsack.printStep())

        knapsack.getRemainingItemsFrom(knapsack.lastItemIndex).forEach { item ->
            if (knapsack.hasRoomFor(item)) {
                val knapsackWithItem = Knapsack(knapsack)
                knapsackWithItem.add(item)
                doSolve(knapsackWithItem)
            }
        }
    }

}