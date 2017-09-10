package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.AbstractComponent
import com.anahoret.nirvanaplayer.components.appendChild
import com.anahoret.nirvanaplayer.model.TrackDto
import kotlinx.html.*
import kotlinx.html.dom.create
import kotlin.browser.document

class ControlsPanel: AbstractComponent() {

  override val element = document.create.div("player-controls")

  private val audio = Audio()
  private val trackLabel = TrackLabel()
  private val timeSlider = TimeSlider(audio)

  init {
    element.appendChild(trackLabel)
    element.appendChild(PlaybackControls(audio, timeSlider))
  }

  fun setTrack(trackDto: TrackDto) {
    audio.pause()
    audio.setSrc(trackDto.url)
    trackLabel.setTrack(trackDto)
    timeSlider.setMaxTime(trackDto.duration)
  }

  fun addTrackEndedListener(l: () -> Unit) {
    audio.addTrackEndedListener(l)
  }

  fun play() = audio.play()

}
