package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlin.browser.document

class Track(val trackDto: TrackDto, margin: Int): AbstractComponent() {

  override val element = document.create.div("player-media-library-track") {
    style = "margin-left: ${margin}px"

    attributes["data-track-url"] = trackDto.url
    attributes["data-track-title"] = trackDto.title

    span("track-title") {
      +"${trackDto.title} (${trackDto.duration})"
      onClickFunction = {
        fireTrackClickEvent(TrackClickEvent(trackDto))
      }
    }
    span {
      +"X"
      onClickFunction = {
        fireTrackRemoveButtonClickEvent(TrackRemoveButtonClickEvent(trackDto))
      }
    }
  }

  private val trackClickListeners = ArrayList<(Track.TrackClickEvent) -> Unit>()
  private val trackRemoveButtonClickListeners = ArrayList<(Track.TrackRemoveButtonClickEvent) -> Unit>()

  class TrackClickEvent(val trackDto: TrackDto)
  class TrackRemoveButtonClickEvent(val trackDto: TrackDto)

  fun addTrackClickListener(l: (Track.TrackClickEvent) -> Unit) {
    trackClickListeners.add(l)
  }

  private fun fireTrackClickEvent(event: Track.TrackClickEvent) {
    trackClickListeners.forEach { it(event) }
  }

  fun addTrackRemoveButtonClickListener(l: (Track.TrackRemoveButtonClickEvent) -> Unit) {
    trackRemoveButtonClickListeners.add(l)
  }

  private fun fireTrackRemoveButtonClickEvent(event: Track.TrackRemoveButtonClickEvent) {
    trackRemoveButtonClickListeners.forEach { it(event) }
  }

}
