package com.norby.nirvana_player.components

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
      root.appendChild(playList.playlistDiv)
      root.appendChild(controls.controlsDiv)
    }
  }

  override fun onTrackClicked(trackClickEvent: PlayList.TrackClickEvent) {
    controls.setTrack(trackClickEvent.title, trackClickEvent.url, trackClickEvent.duration)
  }

}