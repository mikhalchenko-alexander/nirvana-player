package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.TextLabel
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlin.browser.document
import kotlin.js.Math

class TimeLabel: TextLabel() {

  override val element = document.create.div("current-track-title") { text }

  init {
    element.appendChild(text)
    setTime(0.0)
  }

  fun setTime(time: Double) {
    val timeString =
            listOf(
                    Math.floor(time / 3600),
                    Math.floor(time % 3600 / 60),
                    Math.floor(time % 60))
                    .map { if (it < 10) "0$it" else "$it" }
                    .joinToString(":")
    setText(timeString)
  }
}
