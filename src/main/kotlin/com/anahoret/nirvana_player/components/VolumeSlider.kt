package com.anahoret.nirvana_player.components

import org.w3c.dom.HTMLAudioElement

class VolumeSlider(audio: HTMLAudioElement):
  NoUiSlider(
    options = NoUiSlider.Options(min = 0.0, max = 1.0, step = 0.01, start = 1.0),
    onValueChange = { audio.volume = it })
