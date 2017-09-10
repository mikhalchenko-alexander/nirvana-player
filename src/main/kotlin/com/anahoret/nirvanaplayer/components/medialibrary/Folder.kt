package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.components.appendChild
import com.anahoret.nirvanaplayer.model.FolderDto
import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.html.span
import kotlinx.html.style
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class Folder(folderDto: FolderDto, margin: Int): AbstractComponent() {

  override val element: HTMLElement =
    document.create.div("player-media-library-folder") {
      style = "margin-left: ${margin}px"
      span("folder-name") {
        +folderDto.name
      }
      span {
        +"=>"

        onClickFunction = {
          fireFolderPlaylistButtonClickEvent(Folder.FolderPlaylistButtonClickEvent(folderDto))
        }
      }
    }

  private val trackPlaylistButtonClickListeners = ArrayList<(Track.TrackPlaylistButtonClickEvent) -> Unit>()
  private val folderPlaylistButtonClickListeners = ArrayList<(Folder.FolderPlaylistButtonClickEvent) -> Unit>()

  init {
    val folderNameSpan = element.querySelector(".folder-name") as HTMLElement
    folderNameSpan.onclick = { element.classList.toggle("opened") }

    folderDto.folders?.forEach {
      val folder = Folder(it, margin + 10)
      folder.addTrackPlaylistButtonClickListener(this::fireTrackPlaylistButtonClickEvent)
      folder.addFolderPlaylistButtonClickListener(this::fireFolderPlaylistButtonClickEvent)
      element.appendChild(folder)
    }
    
    folderDto.tracks?.forEach {
      val track = Track(it, margin)
      track.addTrackPlaylistButtonClickListener(this::fireTrackPlaylistButtonClickEvent)
      element.appendChild(track)
    }
  }

  class FolderPlaylistButtonClickEvent(val folderDto: FolderDto)

  fun addFolderPlaylistButtonClickListener(l: (Folder.FolderPlaylistButtonClickEvent) -> Unit) {
    folderPlaylistButtonClickListeners.add(l)
  }

  private fun fireFolderPlaylistButtonClickEvent(event: Folder.FolderPlaylistButtonClickEvent) {
    folderPlaylistButtonClickListeners.forEach { it(event) }
  }

  fun addTrackPlaylistButtonClickListener(l: (Track.TrackPlaylistButtonClickEvent) -> Unit) {
    trackPlaylistButtonClickListeners.add(l)
  }

  private fun fireTrackPlaylistButtonClickEvent(event: Track.TrackPlaylistButtonClickEvent) {
    trackPlaylistButtonClickListeners.forEach { it(event) }
  }

}
