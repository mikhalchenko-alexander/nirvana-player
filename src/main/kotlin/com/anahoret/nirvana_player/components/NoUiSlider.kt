package com.anahoret.nirvana_player.components

import org.w3c.dom.Element

class NoUiSlider(private val element: Element, options: Options, onValueChange: (Double) -> Unit) {

  private var dragging = false

  companion object {
    val noUiSlider: dynamic = js("require('nouislider/distribute/nouislider.js');")
    init {
      js("require('nouislider/distribute/nouislider.css');")
    }
  }

  init {
    noUiSlider.create(element, options)

    element.asDynamic().noUiSlider.on("change", {
      val value = element.asDynamic().noUiSlider.get().unsafeCast<Double>()
      onValueChange(value)
    })

    element.asDynamic().noUiSlider.on("start", { dragging = true }.asDynamic())
    element.asDynamic().noUiSlider.on("end", { dragging = false }.asDynamic())
  }

  fun setMaxValue(maxValue: Double): Unit {
    element.asDynamic().noUiSlider.updateOptions(Options(0.0, maxValue, 0.1, 0.0))
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

  fun setValue(currentTime: Double): Unit {
    if (!dragging) {
      element.asDynamic().noUiSlider.set(currentTime)
    }
  }

}
