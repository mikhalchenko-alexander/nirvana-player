package com.anahoret.nirvana_player.components

import org.w3c.dom.Element

class NoUiSlider(element: Element, options: Options, onValueChange: (Double) -> Unit) {

  companion object {
    val noUiSlider = js("require('nouislider/distribute/nouislider.js');")
    init {
      js("require('nouislider/distribute/nouislider.css');")
    }
  }

  init {
    noUiSlider.create(element, options)

    element.asDynamic().noUiSlider.on("update", {
      val value = element.asDynamic().noUiSlider.get().unsafeCast<Double>()
      onValueChange(value)
      println(value)
    })
  }

  class Options(
    min: Double,
    max: Double,
    val step: Double,
    val start: Double = 0.0) {
    val range = Range(min, max)
    val connect = arrayOf(true, false)
  }

  class Range(val min: Double,
              val max: Double)

}
