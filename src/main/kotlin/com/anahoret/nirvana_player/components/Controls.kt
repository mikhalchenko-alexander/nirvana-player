package com.anahoret.nirvana_player.components

import com.anahoret.nirvana_player.model.Track
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
  private lateinit var timeSlider: TimeSlider

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
    NoUiSlider(volumeControl, NoUiSlider.Options(min = 0.0, max = 1.0, step = 0.01, start = 1.0), { audio.volume = it })

    val timeControl = document.create.div()
    controlsPanel.appendChild(timeControl)
    timeSlider = TimeSlider(timeControl, NoUiSlider.Options(min = 0.0, max = 0.1, step = 0.1), { audio.currentTime = it })

    audio.ontimeupdate = { timeSlider.setValue(audio.currentTime) }

    return controlsPanel
  }

  fun setTrack(track: Track): Unit {
    audio.pause()
    audio.src = track.url

    trackLabel.clear()
    trackLabel.appendChild(document.createTextNode(track.title))

    timeSlider.setMaxTime(track.duration)
  }

}
