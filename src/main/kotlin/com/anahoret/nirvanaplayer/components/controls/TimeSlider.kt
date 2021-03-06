package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.NoUiSlider

class TimeSlider(audio: Audio):
  NoUiSlider(
    onValueChange = { audio.setCurrentTime(it) }) {

  fun setMaxTime(maxTime: String) {
    val timeParts = maxTime.split(":")
    val hours = timeParts[0].toDouble()
    val minutes = timeParts[1].toDouble()
    val seconds = timeParts[2].toDouble()
    val maxValue = hours * 3600 + minutes * 60 + seconds
    setMaxValue(maxValue)
  }

}
