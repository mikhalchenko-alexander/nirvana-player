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

class Folder(folderDto: FolderDto, margin: Int, private val onTrackClick: (Track.TrackClickEvent) -> Unit): AbstractComponent() {

  override val element: HTMLElement =
    document.create.div("player-media-library-folder") { style = "margin-left: ${margin}px" }

  init {
    val folderNameSpan = document.create.span("folder-name") { +folderDto.name }
    folderNameSpan.onclick = { element.toggleClass("opened") }
    element.appendChild(folderNameSpan)
    folderDto.folders?.forEach { element.appendChild(Folder(it, margin + 10, onTrackClick)) }
    folderDto.tracks?.forEach { element.appendChild(Track(it, margin, onTrackClick)) }
  }

}
