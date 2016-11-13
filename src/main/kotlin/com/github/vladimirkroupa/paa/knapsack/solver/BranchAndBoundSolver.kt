package com.github.vladimirkroupa.paa.knapsack.solver

import com.github.vladimirkroupa.paa.knapsack.Item
import com.github.vladimirkroupa.paa.knapsack.Knapsack
import com.github.vladimirkroupa.paa.knapsack.Problem
import com.github.vladimirkroupa.paa.knapsack.solver.branchandbound.ItemFractionsBoundingFunction
import com.github.vladimirkroupa.paa.knapsack.solver.branchandbound.Node
import java.util.*

/**
 * https://www0.gsb.columbia.edu/mygsb/faculty/research/pubfiles/4407/kolesar_branch_bound.pdf
 */
class BranchAndBoundSolver(problemInstance: Problem) : KnapsackSolver(problemInstance) {

    private val itemOrderingByValueWeightRatio: List<Item>

    init {
        itemOrderingByValueWeightRatio = problemInstance.items.sortedWith(Collections.reverseOrder(java.util.Comparator.comparingDouble { item -> item.valueToWeightRatio }))
    }

    override fun solve(): Knapsack {
        val boundingFunction = ItemFractionsBoundingFunction(itemOrderingByValueWeightRatio)
        val initial = Node(Knapsack(problemInstance), boundingFunction)
        val solution = findSolutionNode(initial)
        return solution!!.knapsack
    }

    private fun findSolutionNode(node: Node): Node? {
        if (node.upperBound < 0) {
            return null
        }
        if (node.isOptimalSolution()) {
            if (node.knapsack.totalWeight > node.knapsack.capacity) {
                return null
            }
            return node
        }
        val pivot = selectPivotItem(node)
        val pivotIncluded = node.include(pivot)
        val fromIncluded = findSolutionNode(pivotIncluded)
        if (fromIncluded != null) {
            return fromIncluded
        } else {
            val pivotExcluded = node.exclude(pivot)
            return findSolutionNode(pivotExcluded)
        }
    }

    private fun selectPivotItem(node: Node): Item
            = itemOrderingByValueWeightRatio.first { item -> !node.knapsack.contains(item) && !node.excludedItems.contains(item) }

}

