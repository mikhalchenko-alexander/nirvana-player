package com.anahoret.nirvanaplayer.components

import kotlin.browser.document
import kotlinx.html.dom.create
import kotlinx.html.*
import com.anahoret.nirvanaplayer.common.require

open class NoUiSlider(options: Options, onValueChange: (Double) -> Unit): AbstractComponent() {
  private var dragging = false
  override final val element = document.create.div()
  private val slider: dynamic

  companion object {
    val noUiSlider: dynamic = require("nouislider/distribute/nouislider.js")
    init {
      require("nouislider/distribute/nouislider.css")
    }
  }

  init {
    noUiSlider.create(element, options)
    slider = element.asDynamic().noUiSlider

    on("change", {
      val value = slider.get().unsafeCast<Double>()
      onValueChange(value)
    })

    on("start", { dragging = true })
    on("end", { dragging = false })
  }

  private fun on(event: String, body: () -> Unit) {
    slider.on(event, body)
  }

  fun setMaxValue(maxValue: Double) {
    slider.updateOptions(Options(0.0, maxValue, 0.1, 0.0))
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

  fun setValue(value: Double) {
    if (!dragging) {
      slider.set(value)
    }
  }

}
