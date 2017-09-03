package com.anahoret.nirvana_player.components

import com.anahoret.nirvana_player.model.Folder
import com.anahoret.nirvana_player.model.Track
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class MediaLibrary(mediaLibraryUrl: String, val onTrackClick: (TrackClickEvent) -> Unit): PlayerComponent() {

  override val element = document.create.div("player-media-library")

  init {
    loadMediaLibrary(mediaLibraryUrl) { element.appendChild(renderFolder(it)) }
  }

  private fun HTMLElement.toggleClass(cl: String): Unit {
    if (classList.contains(cl)) { classList.remove(cl) }
    else { classList.add(cl) }
  }

  private fun renderFolder(folder: Folder, margin: Int = 0): HTMLElement {
    val folderDiv = document.create.div("player-media-library-folder") { style = "margin-left: ${margin}px" }
    val folderNameSpan = document.create.span("folder-name") { +folder.name }
    folderNameSpan.onclick = { folderDiv.toggleClass("opened") }
    folderDiv.appendChild(folderNameSpan)
    folder.folders?.forEach { folderDiv.appendChild(renderFolder(it, margin + 10)) }
    folder.tracks?.forEach { folderDiv.appendChild(renderTrack(it, margin)) }

    return folderDiv
  }

  private fun renderTrack(track: Track, margin: Int): HTMLElement {
    return document.create.div("player-media-library-track") {
      style = "margin-left: ${margin}px"

      attributes["data-track-url"] = track.url
      attributes["data-track-title"] = track.title

      span("track-title") { +"${track.title} (${track.duration})" }

      onClickFunction = { fireTrackClicked(track) }
    }
  }

  private fun fireTrackClicked(track: Track): Unit {
    val event = TrackClickEvent(track)
    onTrackClick(event)
  }

  private fun loadMediaLibrary(mediaLibraryUrl: String, onSuccess: (Folder) -> Unit): Unit {
    val xhr = XMLHttpRequest()
    xhr.open("GET", mediaLibraryUrl, true)
    xhr.send()

    xhr.onreadystatechange = {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        if (xhr.status == 200.toShort()) {
          val folder = JSON.parse<Folder>(xhr.responseText)
          onSuccess(folder)
        } else {
          println("Error loading media library. Status ${xhr.status}.")
        }
      }
    }
  }

  class TrackClickEvent(val track: Track)

}
