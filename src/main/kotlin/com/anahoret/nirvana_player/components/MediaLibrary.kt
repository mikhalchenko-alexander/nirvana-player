package com.anahoret.nirvana_player.components

import com.anahoret.nirvana_player.model.FolderDto
import com.anahoret.nirvana_player.model.TrackDto
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class MediaLibrary(mediaLibraryUrl: String, private val onTrackClick: (TrackClickEvent) -> Unit): AbstractComponent() {

  override val element = document.create.div("player-media-library")

  init {
    loadMediaLibrary(mediaLibraryUrl) { element.appendChild(renderFolder(it)) }
  }

  private fun HTMLElement.toggleClass(cl: String): Unit {
    if (classList.contains(cl)) { classList.remove(cl) }
    else { classList.add(cl) }
  }

  private fun renderFolder(folderDto: FolderDto, margin: Int = 0): HTMLElement {
    val folderDiv = document.create.div("player-media-library-folderDto") { style = "margin-left: ${margin}px" }
    val folderNameSpan = document.create.span("folderDto-name") { +folderDto.name }
    folderNameSpan.onclick = { folderDiv.toggleClass("opened") }
    folderDiv.appendChild(folderNameSpan)
    folderDto.folderDtos?.forEach { folderDiv.appendChild(renderFolder(it, margin + 10)) }
    folderDto.trackDtos?.forEach { folderDiv.appendChild(renderTrack(it, margin)) }

    return folderDiv
  }

  private fun renderTrack(trackDto: TrackDto, margin: Int): HTMLElement {
    return document.create.div("player-media-library-trackDto") {
      style = "margin-left: ${margin}px"

      attributes["data-trackDto-url"] = trackDto.url
      attributes["data-trackDto-title"] = trackDto.title

      span("trackDto-title") { +"${trackDto.title} (${trackDto.duration})" }

      onClickFunction = { fireTrackClicked(trackDto) }
    }
  }

  private fun fireTrackClicked(trackDto: TrackDto): Unit {
    val event = TrackClickEvent(trackDto)
    onTrackClick(event)
  }

  private fun loadMediaLibrary(mediaLibraryUrl: String, onSuccess: (FolderDto) -> Unit): Unit {
    val xhr = XMLHttpRequest()
    xhr.open("GET", mediaLibraryUrl, true)
    xhr.send()

    xhr.onreadystatechange = {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        if (xhr.status == 200.toShort()) {
          val folder = JSON.parse<FolderDto>(xhr.responseText)
          onSuccess(folder)
        } else {
          println("Error loading media library. Status ${xhr.status}.")
        }
      }
    }
  }

  class TrackClickEvent(val trackDto: TrackDto)

}
