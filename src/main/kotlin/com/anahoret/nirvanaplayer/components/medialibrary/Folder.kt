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

  private val trackClickListeners = ArrayList<(Track.TrackClickEvent) -> Unit>()

  init {
    val folderNameSpan = document.create.span("folder-name") { +folderDto.name }
    folderNameSpan.onclick = { element.toggleClass("opened") }
    element.appendChild(folderNameSpan)
    folderDto.folders?.forEach {
      val folder = Folder(it, margin + 10)
      folder.addTrackClickListener(this::fireTrackClickedEvent)
      element.appendChild(folder)
    }
    folderDto.tracks?.forEach {
      val track = Track(it, margin)
      track.addTrackClickListener(this::fireTrackClickedEvent)
      element.appendChild(track)
    }
  }

  fun addTrackClickListener(l: (Track.TrackClickEvent) -> Unit) {
    trackClickListeners.add(l)
  }

  private fun fireTrackClickedEvent(event: Track.TrackClickEvent) {
    trackClickListeners.forEach { it(event) }
  }

}
