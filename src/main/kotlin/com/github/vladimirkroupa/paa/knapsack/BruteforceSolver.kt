package com.github.vladimirkroupa.paa.knapsack

class BruteforceSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    var best: Knapsack? = null

    override fun solve(): Knapsack? {
        val knapsack = Knapsack(problemInstance)
        doSolve(knapsack)
        return best
    }

    private fun doSolve(knapsack: Knapsack) {
        if (best == null || knapsack.totalValue > best!!.totalValue) {
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