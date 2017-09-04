package com.anahoret.nirvana_player.components

import com.anahoret.nirvana_player.model.TrackDto
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLAudioElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear
import kotlin.js.Math

class Controls : AbstractComponent() {

  private val audio = document.create.audio() as HTMLAudioElement
  private val trackLabel = document.create.div("current-trackDto-title")
  private lateinit var timeSlider: TimeSlider

  override val element = document.create.div("player-controls")

  init {
    element.appendChild(trackLabel)
    val controls = renderControls()
    element.appendChild(controls)
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

    val volumeControl = VolumeSlider(audio)
    controlsPanel.appendChild(volumeControl)

    val timeSpan = document.create.span { +"00:00:00" }
    timeSlider = TimeSlider(audio)
    controlsPanel.appendChild(timeSpan)
    controlsPanel.appendChild(timeSlider)

    audio.ontimeupdate = { _: Event ->
      val currentTime = audio.currentTime
      val timeString =
        listOf(
          Math.floor(currentTime / 3600),
          Math.floor(currentTime % 3600 / 60),
          Math.floor(currentTime % 60))
          .map { if (it < 10) "0$it" else "$it" }
          .joinToString(":")
      timeSpan.innerHTML = timeString
      timeSlider.setValue(currentTime)
    }
    return controlsPanel
  }

  fun setTrack(trackDto: TrackDto) {
    audio.pause()
    audio.src = trackDto.url

    trackLabel.clear()
    trackLabel.appendChild(document.createTextNode(trackDto.title))

    timeSlider.setMaxTime(trackDto.duration)
  }

}
