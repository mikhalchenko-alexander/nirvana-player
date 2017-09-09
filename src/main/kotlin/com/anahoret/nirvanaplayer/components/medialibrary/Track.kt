package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlin.browser.document

class Track(trackDto: TrackDto, margin: Int): AbstractComponent() {

  override val element = document.create.div("player-media-library-track") {
    style = "margin-left: ${margin}px"

    attributes["data-track-url"] = trackDto.url
    attributes["data-track-title"] = trackDto.title

    span("track-title") { +"${trackDto.title} (${trackDto.duration})" }

    onClickFunction = {
      fireTrackClickedEvent(TrackClickEvent(trackDto))
    }
  }

  private val trackClickListeners = ArrayList<(Track.TrackClickEvent) -> Unit>()

  class TrackClickEvent(val trackDto: TrackDto)

  fun addTrackClickListener(l: (Track.TrackClickEvent) -> Unit) {
    trackClickListeners.add(l)
  }

  private fun fireTrackClickedEvent(event: Track.TrackClickEvent) {
    trackClickListeners.forEach { it(event) }
  }

}
