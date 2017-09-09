package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.components.appendChild
import com.anahoret.nirvanaplayer.components.toggleClass
import com.anahoret.nirvanaplayer.model.FolderDto
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.span
import kotlinx.html.style
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class Folder(folderDto: FolderDto, margin: Int): AbstractComponent() {

  override val element: HTMLElement =
    document.create.div("player-media-library-folder") { style = "margin-left: ${margin}px" }

  private val trackPlaylistButtonClickListeners = ArrayList<(Track.TrackPlaylistButtonClickEvent) -> Unit>()

  init {
    val folderNameSpan = document.create.span("folder-name") { +folderDto.name }
    folderNameSpan.onclick = { element.toggleClass("opened") }
    element.appendChild(folderNameSpan)
    folderDto.folders?.forEach {
      val folder = Folder(it, margin + 10)
      folder.addTrackPlaylistButtonClickListener(this::fireTrackPlaylistButtonClickEvent)
      element.appendChild(folder)
    }
    folderDto.tracks?.forEach {
      val track = Track(it, margin)
      track.addTrackPlaylistButtonClickListener(this::fireTrackPlaylistButtonClickEvent)
      element.appendChild(track)
    }
  }

  fun addTrackPlaylistButtonClickListener(l: (Track.TrackPlaylistButtonClickEvent) -> Unit) {
    trackPlaylistButtonClickListeners.add(l)
  }

  private fun fireTrackPlaylistButtonClickEvent(event: Track.TrackPlaylistButtonClickEvent) {
    trackPlaylistButtonClickListeners.forEach { it(event) }
  }

}
