package com.github.vladimirkroupa.paa.knapsack

class Knapsack(private val problemInstance: Problem,
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

    val remainingItems: Iterable<Item>
        get() = problemInstance.items.filterIndexed { i, item -> (items[i] == false) }

    fun getRemainingItemsFrom(fromIndex: Int): Iterable<Item>
            = problemInstance.items.filterIndexed { i, item -> (i >= fromIndex) and (items[i] == false) }

    fun hasRoomFor(item: Item): Boolean
            = item.weight <= (capacity - totalWeight)

    fun add(item: Item): Knapsack {
        val copy = Knapsack(this)
        copy._add(item)
        return copy
    }

    private fun _add(item: Item) {
        if (items[item.itemIndex]) {
            throw IllegalArgumentException("Item $item already in knapsack.")
        }
        items[item.itemIndex] = true
    }

    fun printStep(): String {
        val padding: String = itemsInside.fold("", { acc, i -> acc + "  " })
        return "$padding$this"
    }

    override fun toString(): String {
        val flagArray = items.toList().map({ flag -> if (flag) 1 else 0 }).joinToString(separator = " ")
        return "[$flagArray] : ${totalValue} ∑(V) / ${totalWeight} ∑(W)"
    }

}