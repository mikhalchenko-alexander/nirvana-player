package com.anahoret.nirvanaplayer.components.controls

import com.anahoret.nirvanaplayer.components.AbstractComponent
import kotlinx.html.audio
import kotlinx.html.dom.create
import org.w3c.dom.HTMLAudioElement
import org.w3c.dom.events.Event
import kotlin.browser.document

class Audio: AbstractComponent() {
  override val element = document.create.audio() as HTMLAudioElement

  fun play() = element.play()
  fun pause() = element.pause()
  fun onTimeUpdate(f: (Event) -> dynamic) {
    element.ontimeupdate = f
  }
  fun currentTime() = element.currentTime
  fun setSrc(src: String) { element.src = src }
  fun setVolume(volume: Double) { element.volume = volume }
  fun setCurrentTime(time: Double) { element.currentTime = time }
}
