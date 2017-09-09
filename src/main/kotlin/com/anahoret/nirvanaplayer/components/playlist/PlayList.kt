package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.dom.create
import kotlinx.html.*
import kotlin.browser.document

class PlayList : AbstractComponent() {

  override val element = document.create.div("player-playlist")

  private val trackClickListeners = ArrayList<(Track.TrackClickEvent) -> Unit>()
  private val tracks = ArrayList<TrackDto>()

  fun addTrack(trackDto: TrackDto) {
    if (!tracks.contains(trackDto)) {
      val track = Track(trackDto, 0)
      track.addTrackClickListener(this::fireTrackClickEvent)
      track.addTrackRemoveButtonClickListener {
        removeChild(track)
        tracks.remove(trackDto)
      }
      appendChild(track)
      tracks.add(trackDto)
    }
  }

  fun addTrackClickListener(l: (Track.TrackClickEvent) -> Unit) {
    trackClickListeners.add(l)
  }

  private fun fireTrackClickEvent(event: Track.TrackClickEvent) {
    trackClickListeners.forEach { it(event) }
  }

}
