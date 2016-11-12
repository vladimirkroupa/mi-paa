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

    private val unprocessedNodesSortedByUpperBound: SortedSet<Node>

    private val itemOrderingByValueWeightRatio: SortedSet<Item>

    init {
        unprocessedNodesSortedByUpperBound = sortedSetOf(java.util.Comparator.comparingInt { node -> node.upperBound })
        itemOrderingByValueWeightRatio = problemInstance.items.toSortedSet(java.util.Comparator.comparingDouble { item -> item.valueToWeightRatio })
    }

    override fun solve(): Knapsack {
        val solution = findSolutionNode()
        return if (solution != null) solution.knapsack else Knapsack(problemInstance)
    }

    private fun findSolutionNode(): Node? {
        val boundingFunction = ItemFractionsBoundingFunction(itemOrderingByValueWeightRatio)

        val initial = Node(1, Knapsack(problemInstance), boundingFunction)
        unprocessedNodesSortedByUpperBound.add(initial)

        val nodeIterator = unprocessedNodesSortedByUpperBound.iterator()
        while (nodeIterator.hasNext()) {
            val node = nodeIterator.next()!!

            if (node.isOptimalSolution()) {
                return node
            }
            val pivot = selectPivotItem(node)
            val pivotExcluded = node.exclude(pivot)
            val pivotIncluded = node.include(pivot)
            unprocessedNodesSortedByUpperBound.add(pivotExcluded)
            unprocessedNodesSortedByUpperBound.add(pivotIncluded)
        }
        // no solution
        return null
    }

    private fun selectPivotItem(node: Node): Item
        = itemOrderingByValueWeightRatio.first { item -> ! node.knapsack.contains(item) }

}

