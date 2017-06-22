package com.anahoret.nirvana_player.components

import org.w3c.dom.Element

class TimeSlider(element: Element, options: NoUiSlider.Options, onValueChange: (Double) -> Unit) {

  private val noUiSlider = NoUiSlider(element, options, onValueChange)

  val setValue = noUiSlider::setValue

  fun setMaxTime(maxTime: String): Unit {
    val timeParts = maxTime.split(":")
    val hours = timeParts[0].toDouble()
    val minutes = timeParts[1].toDouble()
    val seconds = timeParts[2].toDouble()
    noUiSlider.setMaxValue(hours * 3600 + minutes * 60 + seconds)
  }

}
