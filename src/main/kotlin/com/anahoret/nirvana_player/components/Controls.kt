package com.anahoret.nirvana_player.components

import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLAudioElement
import kotlin.browser.document
import kotlin.dom.clear

class Controls {

  private val audio = document.create.audio {
    controls = true
  } as HTMLAudioElement
  private val trackLabel = document.create.div("current-track-title")

  val controlsDiv = document.create.div("player-controls") {
  }

  init {
    controlsDiv.appendChild(trackLabel)
    controlsDiv.appendChild(audio)
  }

  fun setTrack(title: String, url: String, duration: String): Unit {
    audio.pause()
    audio.src = url

    trackLabel.clear()
    trackLabel.appendChild(document.createTextNode(title))
  }

}
