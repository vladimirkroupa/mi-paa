package com.github.vladimirkroupa.paa.knapsack

import java.util.*

class Knapsack(internal val problemInstance: Problem,
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

    val itemsInsideCount: Int
        get() = items.map { isInside -> if (isInside) 1 else 0 }.reduce { i1, i2 -> i1 + i2 }

    val lastItemIndex: Int
        get() = items.lastIndexOf(true)

    val remainingItems: Iterable<Item>
        get() = problemInstance.items.filterIndexed { i, item -> (items[i] == false) }

    val remainingCapacity: Int
        get() = capacity - totalWeight

    fun contains(item: Item): Boolean
            = items[item.itemIndex]

    fun getRemainingItemsFrom(fromIndex: Int): Iterable<Item>
            = problemInstance.items.filterIndexed { i, item -> (i >= fromIndex) and (items[i] == false) }

    fun hasRoomFor(item: Item): Boolean
            = item.weight <= (capacity - totalWeight)

    fun add(item: Item): Knapsack {
        val copy = Knapsack(this)
        copy._add(item.itemIndex)
        return copy
    }

    fun add(itemNo: Int): Knapsack {
        val copy = Knapsack(this)
        copy._add(itemNo - 1)
        return copy
    }

    private fun _add(itemIndex: Int) {
        if (itemIndex !in items.indices) {
            throw IllegalArgumentException("Invalid item index $itemIndex")
        }
        if (items[itemIndex]) {
            throw IllegalArgumentException("Item ${items[itemIndex]} already in knapsack.")
        }
        items[itemIndex] = true
    }


    fun printStep(): String {
        val padding: String = itemsInside.fold("", { acc, i -> acc + "  " })
        return "$padding$this"
    }

    override fun toString(): String {
        val flagArray = items.toList().map({ flag -> if (flag) 1 else 0 }).joinToString(separator = " ")
        return "[$flagArray] : ${totalValue} ∑(V) / ${totalWeight} ∑(W)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Knapsack

        if (!Arrays.equals(items, other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(items)
    }

}