package com.anahoret.nirvanaplayer.components

import com.anahoret.nirvanaplayer.components.controls.ControlsPanel
import com.anahoret.nirvanaplayer.components.medialibrary.MediaLibrary
import com.anahoret.nirvanaplayer.components.playlist.PlayList
import kotlinx.html.div
import kotlin.browser.document
import kotlinx.html.dom.create

class Player {

  init {
    val root = document.getElementById("nirvana-player")
    if (root == null) {
      println("Error. Element with id='#nirvana-player' not found.")
    } else {
      val mediaLibraryUrl = root.getAttribute("data-media-library-url") ?: ""
      val controls = ControlsPanel()
      val mediaLibrary = MediaLibrary(mediaLibraryUrl)
      val playList = PlayList()
      playList.addTrackClickListener { controls.setTrack(it.trackDto) }
      mediaLibrary.addTrackPlaylistButtonClickListener { playList.addTrack(it.trackDto) }
      mediaLibrary.addFolderPlaylistButtonClickListener { playList.addFolder(it.folderDto) }
      controls.addTrackEndedListener {
        val nextTrack = playList.nextTrack()
        if (nextTrack != null) {
          controls.setTrack(nextTrack)
          controls.play()
        }
      }


      val container = document.create.div("player-container")
      val leftBlock = document.create.div("player-left-block")
      val rightBlock = document.create.div("player-right-block")
      container.appendChild(leftBlock)
      container.appendChild(rightBlock)

      leftBlock.appendChild(controls)
      leftBlock.appendChild(mediaLibrary)
      rightBlock.appendChild(playList)

      root.append(container)
    }
  }

}
