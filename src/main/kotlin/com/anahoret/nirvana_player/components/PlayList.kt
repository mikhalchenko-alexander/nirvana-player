package com.anahoret.nirvana_player.components

import kotlinx.html.div
import kotlinx.html.dom.create
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import org.w3c.dom.HTMLElement
import kotlin.browser.document

class PlayList(trackListUrl: String, val trackClickListener: TrackClickListener) {

  val playlistDiv = document.create.div("player-playlist")

  init {
    loadTrackList(trackListUrl) { renderTrackList(it, playlistDiv) }
  }

  private fun renderTrackList(trackList: TrackList, root: HTMLElement, margin: Int = 0): Unit {
    val trackListDiv = document.create.div {
      style = "margin-left: ${margin}px"
      +trackList.name
    }
    trackList.folders.forEach { renderTrackList(it, trackListDiv, margin + 10) }
    trackList.tracks.forEach { track ->
      val trackDiv = document.create.div {
        style = "margin-left: ${margin}px"

        attributes["data-track-url"] = track.url
        attributes["data-track-title"] = track.title

        +"${track.title} (${track.duration})"

        onClickFunction = { fireTrackClicked(track.title, track.url, track.duration) }
      }
      trackListDiv.appendChild(trackDiv)
    }
    root.appendChild(trackListDiv)

  }

  private fun fireTrackClicked(title: String, url: String, duration: String): Unit {
    val event = TrackClickEvent(title, duration, url)
    trackClickListener.onTrackClicked(event)
  }

  private fun loadTrackList(playlistUrl: String, onSuccess: (TrackList) -> Unit): Unit {
    val tnt = TrackList("TNT", emptyArray(),
      arrayOf(
        Track("Track 1", "00:03:21", "url1"),
        Track("Track 2", "00:02:13", "url2"),
        Track("Track 3", "00:04:54", "url3")
      ))

    val highVoltage = TrackList("HighVoltage", emptyArray(),
      arrayOf(
              Track("Track 1", "00:01:12", "url4"),
              Track("Track 2", "00:03:33", "url5"),
              Track("Track 3", "00:02:11", "url6")
      ))
    val acdc = TrackList("ACDC", arrayOf(tnt, highVoltage), emptyArray())


    onSuccess(acdc)
//    val xhr = XMLHttpRequest()
//    xhr.open("GET", playlistUrl, true)
//    xhr.send()
//
//    xhr.onreadystatechange = {
//      if (xhr.readyState == XMLHttpRequest.DONE) {
//        if (xhr.status == 200.toShort()) {
//          val playlist = JSON.parse<TrackList>(xhr.responseText)
//          onSuccess(playlist)
//        } else {
//          println("Error loading playlist. Status ${xhr.status}.")
//        }
//      }
//    }
  }

  private class Track(val title: String, val duration: String, val url: String)
  private class TrackList(val name: String, val folders: Array<TrackList>, val tracks: Array<Track>)

  interface TrackClickListener {
    fun onTrackClicked(trackClickEvent: TrackClickEvent): Unit
  }

  class TrackClickEvent(val title: String, val duration: String, val url: String)

}
