package com.anahoret.nirvanaplayer.components.playlist

import com.anahoret.nirvanaplayer.components.AbstractComponent
import kotlinx.html.dom.create
import kotlinx.html.*
import kotlin.browser.document

class PlayList : AbstractComponent() {

  override val element = document.create.div("player-playlist") { +"TRACKS" }

}
