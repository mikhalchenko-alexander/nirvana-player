package com.anahoret.nirvana_player.components

import org.w3c.dom.HTMLAudioElement

class TimeSlider(audio: HTMLAudioElement):
  NoUiSlider(
    options = NoUiSlider.Options(min = 0.0, max = 0.1, step = 0.1),
    onValueChange = { audio.currentTime = it }) {

  fun setMaxTime(maxTime: String) {
    val timeParts = maxTime.split(":")
    val hours = timeParts[0].toDouble()
    val minutes = timeParts[1].toDouble()
    val seconds = timeParts[2].toDouble()
    setMaxValue(hours * 3600 + minutes * 60 + seconds)
  }

}
