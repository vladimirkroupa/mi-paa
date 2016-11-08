package com.github.vladimirkroupa.paa.knapsack

class BruteforceSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    var best: Knapsack = Knapsack(problemInstance)

    override fun solve(): Knapsack {
        val knapsack = Knapsack(problemInstance)
        doSolve(knapsack)
        return best
    }

    private fun doSolve(knapsack: Knapsack) {
        if (knapsack.totalValue > best.totalValue) {
            best = knapsack
        }

        //println(knapsack.printStep())

        knapsack.getRemainingItemsFrom(knapsack.lastItemIndex).forEach { item ->
            if (knapsack.hasRoomFor(item)) {
                val knapsackWithItem = knapsack.add(item)
                doSolve(knapsackWithItem)
            }
        }
    }

}