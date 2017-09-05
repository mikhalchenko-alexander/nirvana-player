package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.TextLabel
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlin.browser.document

class TrackLabel: TextLabel() {

  override val element = document.create.div("current-track-title")
  init {
    element.appendChild(text)
  }

  fun setTrack(trackDto: TrackDto) { setText(trackDto.title) }

}
