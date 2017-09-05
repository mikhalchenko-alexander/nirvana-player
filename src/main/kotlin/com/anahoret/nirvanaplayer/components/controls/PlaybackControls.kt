package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.AbstractComponent
import kotlinx.html.div
import kotlinx.html.dom.create
import org.w3c.dom.events.Event
import kotlin.browser.document

class PlaybackControls(audio: Audio, timeSlider: TimeSlider): AbstractComponent() {
  override val element = document.create.div("controls-panel")

  init {
    appendChild(PlayButton(audio))
    appendChild(PauseButton(audio))

    val volumeControl = VolumeSlider(audio)
    appendChild(volumeControl)

    val timeLabel = TimeLabel()
    appendChild(timeLabel)
    appendChild(timeSlider)

    audio.onTimeUpdate { _: Event ->
      val currentTime = audio.currentTime()
      timeLabel.setTime(currentTime)
      timeSlider.setValue(currentTime)
    }
  }

}
