package com.anahoret.nirvana_player.components

import kotlinx.html.*
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLElement
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document

class PlayList(trackListUrl: String, val trackClickListener: TrackClickListener) {

  val playlistDiv = document.create.div("player-playlist")

  init {
    loadTrackList(trackListUrl) { playlistDiv.appendChild(renderTrackList(it)) }
  }

  fun HTMLElement.toggleClass(cl: String): Unit {
    if (classList.contains(cl)) { classList.remove(cl) }
    else { classList.add(cl) }
  }

  private fun renderTrackList(trackList: TrackList, margin: Int = 0): HTMLElement {
    val trackListDiv = document.create.div("player-playlist-track-list") { style = "margin-left: ${margin}px" }
    val trackListNameSpan = document.create.span("track-list-name") { +trackList.name }
    trackListNameSpan.onclick = { trackListDiv.toggleClass("opened") }
    trackListDiv.appendChild(trackListNameSpan)
    trackList.folders?.forEach { trackListDiv.appendChild(renderTrackList(it, margin + 10)) }
    trackList.tracks?.forEach { trackListDiv.appendChild(renderTrack(it, margin)) }

    return trackListDiv
  }

  private fun renderTrack(track: Track, margin: Int): HTMLElement {
    return document.create.div("player-playlist-track") {
      style = "margin-left: ${margin}px"

      attributes["data-track-url"] = track.url
      attributes["data-track-title"] = track.title

      span("track-title") { +"${track.title} (${track.duration})" }

      onClickFunction = { fireTrackClicked(track.title, track.url, track.duration) }
    }
  }

  private fun fireTrackClicked(title: String, url: String, duration: String): Unit {
    val event = TrackClickEvent(title, duration, url)
    trackClickListener.onTrackClicked(event)
  }

  private fun loadTrackList(playlistUrl: String, onSuccess: (TrackList) -> Unit): Unit {
    val xhr = XMLHttpRequest()
    xhr.open("GET", playlistUrl, true)
    xhr.send()

    xhr.onreadystatechange = {
      if (xhr.readyState == XMLHttpRequest.DONE) {
        if (xhr.status == 200.toShort()) {
          val playlist = JSON.parse<TrackList>(xhr.responseText)
          onSuccess(playlist)
        } else {
          println("Error loading playlist. Status ${xhr.status}.")
        }
      }
    }
  }

  private class Track(val title: String, val duration: String, val url: String)
  private class TrackList(val name: String, val folders: Array<TrackList>?, val tracks: Array<Track>?)

  interface TrackClickListener {
    fun onTrackClicked(trackClickEvent: TrackClickEvent): Unit
  }

  class TrackClickEvent(val title: String, val duration: String, val url: String)

}
