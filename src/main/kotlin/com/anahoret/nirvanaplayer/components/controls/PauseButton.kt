package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.AbstractComponent
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlin.browser.document

class PauseButton(audio: Audio): AbstractComponent() {
  override val element = document.create.div("btn btn-pause") {
    +"||"
    onClickFunction = { audio.pause() }
  }
}
