package com.anahoret.nirvanaplayer.components

import org.w3c.dom.Element

fun Element.appendChild(abstractComponent: AbstractComponent) {
  this.appendChild(abstractComponent.element)
}
