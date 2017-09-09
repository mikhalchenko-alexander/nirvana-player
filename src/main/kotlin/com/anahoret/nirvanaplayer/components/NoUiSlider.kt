package com.anahoret.nirvanaplayer.components

import kotlin.browser.document
import kotlinx.html.dom.create
import kotlinx.html.*
import com.anahoret.nirvanaplayer.common.require
import org.w3c.dom.HTMLElement

@JsModule("nouislider/distribute/nouislider.js")
private external abstract class NoUiSliderModule {

  companion object {
    fun create(htmlElement: HTMLElement, options: NoUiSliderOptions): NoUiSliderModule
  }

  val options: NoUiSliderOptions
  fun on(event: String, body: () -> Unit)
  fun get(): Double
  fun set(value: Double)
  fun updateOptions(options: NoUiSliderOptions)

}

private class NoUiSliderRange(val min: Double, val max: Double)

private data class NoUiSliderOptions (
  val min: Double = 0.0,
  val max: Double = 1.0,
  val step: Double = 0.1,
  val start: Double = 0.0) {
  val range = NoUiSliderRange(min, max)
  val connect = arrayOf(true, false)
}

open class NoUiSlider(
  min: Double = 0.0,
  max: Double = 1.0,
  step: Double = 0.1,
  start: Double = 0.0,
  onValueChange: (Double) -> Unit): AbstractComponent() {

  private var dragging = false
  override final val element = document.create.div()
  private val slider: NoUiSliderModule

  companion object {
    init {
      require("nouislider/distribute/nouislider.css")
    }
  }

  init {
    slider = NoUiSliderModule.create(element, NoUiSliderOptions(min, max, step, start))
    with(slider) {
      on("change", { onValueChange(slider.get()) })
      on("start", { dragging = true })
      on("end", { dragging = false })
    }
  }

  fun setMaxValue(maxValue: Double) {
    slider.updateOptions(slider.options.copy(max = maxValue))
  }

  fun setValue(value: Double) {
    if (!dragging) {
      slider.set(value)
    }
  }

}
