package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.model.FolderDto
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.dom.create
import kotlinx.html.*
import kotlin.browser.document

class PlayList : AbstractComponent() {

  override val element = document.create.div("player-playlist")

  private val trackClickListeners = ArrayList<(Track.TrackClickEvent) -> Unit>()
  private val tracks = ArrayList<Track>()
  private var activeTrack: Track? = null

  fun addTrack(trackDto: TrackDto) {
    if (tracks.find { it.trackDto == trackDto } == null) {
      val track = Track(trackDto, 0)
      track.addTrackClickListener { event ->
        activeTrack = track
        fireTrackClickEvent(event)
      }
      track.addTrackRemoveButtonClickListener {
        removeChild(track)
        tracks.remove(track)
      }
      appendChild(track)
      tracks.add(track)
    }
  }

  fun nextTrack(): TrackDto? {
    val nextTrackIndex = tracks.indexOf(activeTrack)
    return if (nextTrackIndex < tracks.size - 1) {
      activeTrack = tracks[nextTrackIndex + 1]
      activeTrack?.trackDto
    } else null
  }

  fun addFolder(folderDto: FolderDto) {
    folderDto.folders?.forEach(this::addFolder)
    folderDto.tracks?.forEach(this::addTrack)
  }

  fun addTrackClickListener(l: (Track.TrackClickEvent) -> Unit) {
    trackClickListeners.add(l)
  }

  private fun fireTrackClickEvent(event: Track.TrackClickEvent) {
    trackClickListeners.forEach { it(event) }
  }

}
