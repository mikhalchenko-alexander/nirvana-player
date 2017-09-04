package com.anahoret.nirvana_player.components

import org.w3c.dom.Element
import org.w3c.dom.HTMLElement

fun Element.appendChild(abstractComponent: AbstractComponent) {
  this.appendChild(abstractComponent.element)
}

fun HTMLElement.toggleClass(cl: String) {
  if (classList.contains(cl)) { classList.remove(cl) }
  else { classList.add(cl) }
}
