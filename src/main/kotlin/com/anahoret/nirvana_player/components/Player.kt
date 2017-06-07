package com.anahoret.nirvana_player.components

import kotlinx.html.InputType
import kotlinx.html.dom.create
import kotlinx.html.input
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
      root.appendChild(document.create.input(classes = "slider", type = InputType.range))
    }
  }

  override fun onTrackClicked(trackClickEvent: PlayList.TrackClickEvent) {
    controls.setTrack(trackClickEvent.title, trackClickEvent.url, trackClickEvent.duration)
  }

}
