package com.norby.nirvana_player.components

import kotlinx.html.*
import kotlinx.html.dom.create
import org.w3c.dom.HTMLAudioElement
import kotlin.browser.document
import kotlin.dom.clear

class Controls {

  private val audio = document.create.audio {
    controls = true
  } as HTMLAudioElement
  private val trackLabel = document.create.div()

  val controlsDiv = document.create.div("player-controls") {
  }

  init {
    controlsDiv.insertBefore(trackLabel, controlsDiv.firstChild)
    controlsDiv.appendChild(audio)
  }

  fun setTrack(title: String, url: String, duration: String): Unit {
    audio.pause()
    audio.src = url

    trackLabel.clear()
    trackLabel.appendChild(document.createTextNode(title))
  }

}
