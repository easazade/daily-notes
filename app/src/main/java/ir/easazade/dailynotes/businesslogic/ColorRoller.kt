package ir.easazade.dailynotes.businesslogic

import ir.easazade.dailynotes.utils.Roller

object ColorRoller {

//  const val color1 = "#7BDB74"
//  const val color2 = "#CC6D6D"
//  const val color3 = "#5FB1CB"
//  const val color4 = "#C75574"
//  const val color5 = "#F5E055"
//  const val color6 = "#67B789"
//  const val color7 = "#FF861C"
//  const val color8 = "#AA58E9"

  private val roller = Roller(
      "#7BDB74",
      "#CC6D6D",
      "#5FB1CB",
      "#C75574",
      "#F5E055",
      "#67B789",
      "#FF861C",
      "#AA58E9"
  )

  fun roll(): String = roller.roll()

}