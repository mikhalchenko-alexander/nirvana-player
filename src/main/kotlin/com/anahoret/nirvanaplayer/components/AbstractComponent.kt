package com.anahoret.nirvanaplayer.components

import org.w3c.dom.HTMLElement

abstract class AbstractComponent {

  abstract val element: HTMLElement

  fun appendChild(child: AbstractComponent) { element.appendChild(child) }
  fun removeChild(child: AbstractComponent) { element.removeChild(child.element) }

}
