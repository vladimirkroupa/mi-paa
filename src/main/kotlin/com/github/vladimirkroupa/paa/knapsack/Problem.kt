package com.github.vladimirkroupa.paa.knapsack

class Problem(val knapsackCapacity: Int, val itemWeights: List<Int>, val itemValues: List<Int>) {

    val items: Array<Item>

    init {
        if (itemWeights.size != itemValues.size) {
            val msg = "List of weights and list of values must be of same length. ${itemWeights.size} != ${itemValues.size}"
            throw IllegalArgumentException(msg)
        }
        val itemCount = itemWeights.size
        items = Array(itemCount, { itemIx -> Item(itemIx, this) })
    }

    val itemCount: Int
        get() = items.size

    fun solve() {
        val knapsack = Knapsack(this)
        doSolve(knapsack)
    }

    fun doSolve(knapsack: Knapsack) {
        val padding: String = knapsack.itemsInside.fold("", {acc, i -> acc + "  "})
        println("$padding$knapsack")
        knapsack.getRemainingItemsFrom(knapsack.lastItemIndex).forEach { item ->
            if (knapsack.hasRoomFor(item)) {
                val knapsackWithItem = Knapsack(knapsack)
                knapsackWithItem.add(item)
                doSolve(knapsackWithItem)
            }
        }
    }
}

fun main(args: Array<String>) {
    val p9000 = Problem(100, listOf(18, 42, 88, 3), listOf(114, 136, 192, 223))
    p9000.solve()
}
