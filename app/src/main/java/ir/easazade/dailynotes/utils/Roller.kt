package ir.easazade.dailynotes.utils

import java.util.*

class Roller<T> {

  var queue: LinkedList<T>

  constructor(vararg numbers: T) : super() {
    queue = LinkedList<T>().apply {
      if (numbers.isEmpty()) throw IllegalStateException("Roller queue cannot be empty!")
      numbers.forEach { add(it) }
    }
  }

  constructor(numbers: List<T>) : super() {
    queue = LinkedList<T>().apply {
      if (numbers.isEmpty()) throw IllegalStateException("Roller queue cannot be empty!")
      numbers.forEach { add(it) }
    }
  }

  fun roll(): T {
    val value = queue.poll()!!
    queue.add(value)
    return value
  }

}