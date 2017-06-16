package com.anahoret.nirvana_player.components

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLAudioElement
import org.w3c.dom.HTMLElement
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
    val controls = renderControls()
    controlsDiv.appendChild(controls)
  }

  private fun renderControls(): HTMLElement {
    val controlsPanel = document.create.div("controls-panel")
    val playButton = document.create.div("btn btn-play") {
      +">"
      onClickFunction = { audio.play() }
    }

    val pauseButton = document.create.div("btn btn-pause") {
      +"||"
      onClickFunction = { audio.pause() }
    }

    controlsPanel.appendChild(playButton)
    controlsPanel.appendChild(pauseButton)

    val volumeControl = document.create.div()
    controlsPanel.appendChild(volumeControl)
    NoUiSlider(volumeControl, NoUiSlider.Options(0.0, 1.0, 0.01), { audio.volume = it })

    return controlsPanel
  }

  fun setTrack(title: String, url: String, duration: String): Unit {
    audio.pause()
    audio.src = url

    trackLabel.clear()
    trackLabel.appendChild(document.createTextNode(title))
  }

}
