package com.anahoret.nirvana_player.components

import kotlin.browser.document

class Player: PlayList.TrackClickListener {

  private val controls = Controls()

  init {
    val root = document.getElementById("nirvana-player")
    if (root == null) {
      println("Error. Element with id='#nirvana-player' not found.")
    } else {
      val playlistUrl = root.getAttribute("data-playlist-url") ?: ""
      val playList = PlayList(playlistUrl, this)
      root.appendChild(controls.controlsDiv)
      root.appendChild(playList.playlistDiv)
    }
  }

  override fun onTrackClicked(trackClickEvent: PlayList.TrackClickEvent) {
    controls.setTrack(trackClickEvent.title, trackClickEvent.url, trackClickEvent.duration)
  }

}
