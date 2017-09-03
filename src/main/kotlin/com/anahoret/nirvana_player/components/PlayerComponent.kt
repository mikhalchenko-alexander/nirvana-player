package com.anahoret.nirvana_player.components

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

abstract class PlayerComponent {

  abstract val element: HTMLElement

}

fun Element.appendChild(playerComponent: PlayerComponent) {
  this.appendChild(playerComponent.element)
}
