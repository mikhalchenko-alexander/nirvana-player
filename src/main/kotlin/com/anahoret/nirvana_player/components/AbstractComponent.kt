package com.anahoret.nirvana_player.components

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

abstract class AbstractComponent {

  abstract val element: HTMLElement

}

fun Element.appendChild(abstractComponent: AbstractComponent) {
  this.appendChild(abstractComponent.element)
}
