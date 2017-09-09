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
    span {
      +"==>"
      
      onClickFunction = {
        fireTrackPlaylistButtonClickEvent(TrackPlaylistButtonClickEvent(trackDto))
      }
    }
  }

  private val trackPlaylistButtonClickListeners = ArrayList<(Track.TrackPlaylistButtonClickEvent) -> Unit>()

  class TrackPlaylistButtonClickEvent(val trackDto: TrackDto)

  fun addTrackPlaylistButtonClickListener(l: (Track.TrackPlaylistButtonClickEvent) -> Unit) {
    trackPlaylistButtonClickListeners.add(l)
  }

  private fun fireTrackPlaylistButtonClickEvent(event: Track.TrackPlaylistButtonClickEvent) {
    trackPlaylistButtonClickListeners.forEach { it(event) }
  }

}
