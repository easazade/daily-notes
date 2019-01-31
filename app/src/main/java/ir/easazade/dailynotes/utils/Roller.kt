package ir.easazade.dailynotes.utils

import java.util.*

class Roller<T>(vararg numbers: T) {

  private val queue = LinkedList<T>().apply {
    if (numbers.isEmpty()) throw IllegalStateException("Roller queue cannot be empty!")
    numbers.forEach { add(it) }
  }

  fun roll(): T {
    val value = queue.poll()!!
    queue.add(value)
    return value
  }

}