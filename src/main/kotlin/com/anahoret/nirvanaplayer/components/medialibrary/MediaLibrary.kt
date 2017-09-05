package com.anahoret.nirvanaplayer.components.medialibrary

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.components.appendChild
import com.anahoret.nirvanaplayer.model.FolderDto
import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class MediaLibrary(mediaLibraryUrl: String, private val onTrackClick: (Track.TrackClickEvent) -> Unit): AbstractComponent() {

  override val element = document.create.div("player-media-library")

  init {
    loadMediaLibrary(mediaLibraryUrl) { element.appendChild(Folder(it, 0, onTrackClick)) }
  }

  private fun loadMediaLibrary(mediaLibraryUrl: String, onSuccess: (FolderDto) -> Unit) {
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

}
