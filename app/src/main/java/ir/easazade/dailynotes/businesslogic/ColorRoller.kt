package ir.easazade.dailynotes.businesslogic

import ir.easazade.dailynotes.businesslogic.entities.Note
import ir.easazade.dailynotes.utils.Roller

object ColorRoller {
  private val colorList = listOf(
      "#7BDB74",
      "#CC6D6D",
      "#5FB1CB",
      "#C75574",
      "#F5E055",
      "#67B789",
      "#FF861C",
      "#AA58E9"
  )

  private var roller = Roller(
      "#7BDB74",
      "#CC6D6D",
      "#5FB1CB",
      "#C75574",
      "#F5E055",
      "#67B789",
      "#FF861C",
      "#AA58E9"
  )

  fun set(lastNote: Note) {
    var lastColorIndex = -1
    for (i in 0 until colorList.size) {
      if (colorList[i] == lastNote.color)
        lastColorIndex = i
    }
    val orederedList = mutableListOf<String>()
    if (lastColorIndex != -1) {
      for (i in lastColorIndex + 1 until colorList.size) {
        orederedList.add(colorList[i])
      }
      for (i in 0 until lastColorIndex + 1) {
        orederedList.add(colorList[i])
      }
      roller = Roller(orederedList)
    }

  }

  fun roll(): String = roller.roll()

}