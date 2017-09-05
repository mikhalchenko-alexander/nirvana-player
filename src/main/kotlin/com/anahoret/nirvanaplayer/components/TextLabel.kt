package com.anahoret.nirvanaplayer.components

import kotlin.browser.document

abstract class TextLabel: AbstractComponent() {
  protected val text = document.createTextNode("")

  fun setText(textContent: String) {
    text.textContent = textContent
  }
}
