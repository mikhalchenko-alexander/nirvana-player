package com.anahoret.nirvana_player.components

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class MediaLibrary(mediaLibraryUrl: String, val onTrackClick: (TrackClickEvent) -> Unit) {

  val mediaLibraryDiv = document.create.div("player-media-library")

  init {
    loadMediaLibrary(mediaLibraryUrl) { mediaLibraryDiv.appendChild(renderFolder(it)) }
  }

  fun HTMLElement.toggleClass(cl: String): Unit {
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

      onClickFunction = { fireTrackClicked(track.title, track.url, track.duration) }
    }
  }

  private fun fireTrackClicked(title: String, url: String, duration: String): Unit {
    val event = TrackClickEvent(title, duration, url)
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

  private class Track(val title: String, val duration: String, val url: String)
  private class Folder(val name: String, val folders: Array<Folder>?, val tracks: Array<Track>?)

  class TrackClickEvent(val title: String, val duration: String, val url: String)

}
