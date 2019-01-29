package ir.easazade.dailynotes.utils

import java.util.*


fun <T> MutableList<T>.removeIfExists(item: T) {
    val index = Stack<Int>()
    for (i in 0 until size)
        if (get(i) == item)
            index.add(i)
    if (index.isNotEmpty()) {
        removeAt(index.pop())
        removeIfExists(item)
    }
}

class ListUtils {

    companion object {
        fun <T> getPagination(list: MutableList<T>, page: Int, count: Int): MutableList<T> {
            val from = count * (if (page < 1) 0 else (page - 1))
            val until = count * (if (page < 1) 1 else page)
            if (from in 0 until list.size) {
                return if (until in 0 until list.size) {
                    list.subList(from, until)
                } else {
                    list.subList(from, list.size)
                }
            }
            return mutableListOf()
        }

//        fun sortByDate(list: MutableList<Design>): MutableList<Design> =
//            list.apply {
//                sortWith(Comparator { d1, d2 ->
//                    when {
//                        d1.datePosted.time > d2.datePosted.time -> -1
//                        d1.datePosted.time < d2.datePosted.time -> 1
//                        d1.datePosted.time == d2.datePosted.time -> 0
//                        else -> 0
//                    }
//                })
//            }
//
//        fun hasDesign(list: MutableList<Design>, design: Design): Boolean =
//            list.run {
//                var isMatch = false
//                forEach {
//                    if (it.matches(design))
//                        isMatch = true
//                }
//                return@run isMatch
//            }


        fun <T> compareLists(
            first: List<T>,
            second: List<T>,
            predicate: (t1: T, t2: T) -> Boolean
        ): Pair<MutableList<T>, MutableList<T>> {
            val firstListDifferences = mutableListOf<T>()
            val secondListDifferences = mutableListOf<T>()
            for (i in 0 until first.size) {
                val firstVal = first[i]
                var secondListHasValueOfFirstList = false
                for (j in 0 until second.size) {
                    val secondVal = second[j]
                    if (predicate(firstVal, secondVal))
                        secondListHasValueOfFirstList = true
                }
                if (!secondListHasValueOfFirstList)
                    firstListDifferences.add(firstVal)
            }
            for (k in 0 until second.size) {
                val secondVal = second[k]
                var firstListHasValueOfSecondList = false
                for (l in 0 until first.size) {
                    val firstVal = first[l]
                    if (predicate(secondVal, firstVal))
                        firstListHasValueOfSecondList = true
                }
                if (!firstListHasValueOfSecondList)
                    secondListDifferences.add(secondVal)
            }
            return Pair(firstListDifferences, secondListDifferences)
        }

        fun <T> removeIf(list: MutableList<T>, predicate: (t: T) -> Boolean): List<Int> {
            val indexes = Stack<Int>()
            list.forEach {
                if (predicate(it))
                    indexes.add(list.indexOf(it))
            }
            val copiedIndexes = mutableListOf<Int>()
            copiedIndexes.addAll(indexes)
            while (indexes.isNotEmpty()) {
                val index = indexes.pop()
                list.removeAt(index)
            }
            return copiedIndexes
        }

        fun <T> removeIfMatch(
            list: MutableList<T>,
            matchingItems: List<T>,
            predicate: (listItem: T, matchingItem: T) -> Boolean
        ): List<Int> {
            return removeIf(list) { listItem ->
                var flag = false
                matchingItems.forEach { matchingItem ->
                    if (predicate(listItem, matchingItem))
                        flag = true
                }
                flag
            }
        }

        /***
         * add items to the list from newItems if they do not match the predicate with any items in list
         * @return list of indexes from newItems list that were added to the list
         */
        fun <T> addAllIfNotExists(
            list: MutableList<T>,
            newItems: List<T>,
            matchPredicate: (t1: T, t2: T) -> Boolean
        ): List<Int> {
            val indexOfNewItemsCanBeAdded = Stack<Int>()
            for (i in 0 until newItems.size) {
                var canBeAdded = true
                for (j in 0 until list.size) {
                    if (matchPredicate(newItems[i], list[j]))
                        canBeAdded = false
                }
                if (canBeAdded) indexOfNewItemsCanBeAdded.add(i)
            }
            val addedItemsIndexes = mutableListOf<Int>().apply { addAll(indexOfNewItemsCanBeAdded) }
            while (indexOfNewItemsCanBeAdded.isNotEmpty()) {
                val index = indexOfNewItemsCanBeAdded.pop()
                list.add(newItems[index])
            }
            return addedItemsIndexes
        }

        fun <T> contains(
            list: MutableList<T>,
            item: T,
            matchingPredicate: (t1: T, t2: T) -> Boolean
        ): Boolean {
            var lisHasItem = false
            list.forEach { listItem ->
                if (matchingPredicate(listItem, item))
                    lisHasItem = true
            }
            return lisHasItem
        }

        fun <T> firstMatch(
            list: List<T>,
            matchingPredicate: (listItem: T) -> Boolean
        ): T? {
            var match: T? = null
            list.forEach { listItem ->
                if (matchingPredicate(listItem))
                    match = listItem
            }
            return match
        }

    }

}