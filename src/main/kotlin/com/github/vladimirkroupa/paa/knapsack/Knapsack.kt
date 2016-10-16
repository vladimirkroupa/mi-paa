package com.github.vladimirkroupa.paa.knapsack

class Knapsack(val problemInstance: Problem,
               private val items: BooleanArray = BooleanArray(problemInstance.itemCount, { false })) {

    constructor(knapsack: Knapsack) : this(knapsack.problemInstance, knapsack.items.clone())

    val capacity: Int
        get() = problemInstance.knapsackCapacity

    val totalValue: Int
        get() = itemsInside.fold(0, { valueAcc, item -> valueAcc + item.value })

    val totalWeight: Int
        get() = itemsInside.fold(0, { weightAcc, item -> weightAcc + item.weight })

    val itemsInside: Iterable<Item>
        get() = problemInstance.items.filterIndexed { i, item -> items[i] == true }

    val lastItemIndex: Int
        get() = items.lastIndexOf(true)

    fun getRemainingItemsFrom(fromIndex: Int): Iterable<Item>
        = problemInstance.items.filterIndexed { i, item -> (i >= fromIndex) and (items[i] == false) }

    fun hasRoomFor(item: Item): Boolean
            = item.weight <= (capacity - totalWeight)

    fun add(item: Item) {
        if (items[item.itemIndex]) {
            throw IllegalArgumentException("Item $item already in knapsack.")
        }
        items[item.itemIndex] = true
    }

    override fun toString(): String {
        val flagArray = items.toList().map({ flag -> if (flag) 1 else 0 }).joinToString(separator = " ")
        return "[$flagArray] : ${totalValue} ∑(V) / ${totalWeight} ∑(W)"
    }

}