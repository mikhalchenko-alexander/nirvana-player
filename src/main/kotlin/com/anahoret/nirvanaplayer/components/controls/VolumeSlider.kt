package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.NoUiSlider

class VolumeSlider(audio: Audio):
  NoUiSlider(
    start = 1.0,
    onValueChange = { audio.setVolume(it) })
