package com.anahoret.nirvana_player.components

import kotlin.browser.document

class Player {

  init {
    val root = document.getElementById("nirvana-player")
    if (root == null) {
      println("Error. Element with id='#nirvana-player' not found.")
    } else {
      val controls = Controls()
      val mediaLibraryUrl = root.getAttribute("data-media-library-url") ?: ""
      val mediaLibrary = MediaLibrary(mediaLibraryUrl, { controls.setTrack(it.trackDto) })
      root.appendChild(controls)
      root.appendChild(mediaLibrary)
    }
  }

}
