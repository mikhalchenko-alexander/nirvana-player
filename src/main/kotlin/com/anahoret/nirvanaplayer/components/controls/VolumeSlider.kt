package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.NoUiSlider

class VolumeSlider(audio: Audio):
  NoUiSlider(
    options = Options(min = 0.0, max = 1.0, step = 0.01, start = 1.0),
    onValueChange = { audio.setVolume(it) })
