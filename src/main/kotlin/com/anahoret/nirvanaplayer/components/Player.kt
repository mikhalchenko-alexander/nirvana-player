package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.components.controls.ControlsPanel
import com.anahoret.nirvanaplayer.components.medialibrary.MediaLibrary
import com.anahoret.nirvanaplayer.components.playlist.PlayList
import kotlin.browser.document

class Player {

  init {
    val root = document.getElementById("nirvana-player")
    if (root == null) {
      println("Error. Element with id='#nirvana-player' not found.")
    } else {
      val mediaLibraryUrl = root.getAttribute("data-media-library-url") ?: ""
      val controls = ControlsPanel()
      val mediaLibrary = MediaLibrary(mediaLibraryUrl, { controls.setTrack(it.trackDto) })
      val playList = PlayList()
      root.appendChild(controls)
      root.appendChild(mediaLibrary)
      root.appendChild(playList)
    }
  }

}
