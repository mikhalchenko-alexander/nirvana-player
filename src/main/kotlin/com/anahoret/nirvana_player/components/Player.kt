package com.anahoret.nirvana_player.components

import kotlin.browser.document

class Player {

  private val controls = Controls()

  init {
    val root = document.getElementById("nirvana-player")
    if (root == null) {
      println("Error. Element with id='#nirvana-player' not found.")
    } else {
      val mediaLibraryUrl = root.getAttribute("data-media-library-url") ?: ""
      val mediaLibrary = MediaLibrary(mediaLibraryUrl, { controls.setTrack(it.track) })
      root.appendChild(controls.controlsDiv)
      root.appendChild(mediaLibrary.mediaLibraryDiv)
    }
  }

}
