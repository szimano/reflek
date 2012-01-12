package org.szimano

import scala.swing._
import event.ButtonClicked

import BazaLekow._

/**
 *
 * User: szimano
 */

object Reflek extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Reflek"
    val button = new Button {
      text = "Click me"
    }
    val label = new Label {
      text = "No button clicks registered"
    }

    val leki = readAll()

    val initial = new Array[Array[String]](leki.size)

    var i = 0;

    for (lek <- leki) {
      initial(i) = Array(lek.substancja, lek.nazwa, lek.opakowanie, lek.grupa, lek.wskazania, lek.refundacja)
      i = i + 1
    }
    
    val names = Array("Substancja", "Nazwa i postać", "Opakowanie", "Grupa", "Zakres", "Refundacja")

    var memTable = new Table(initial.asInstanceOf[Array[Array[Any]]], names.asInstanceOf[Array[Any]])

    val scrollPane = new ScrollPane(memTable)

    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      contents += button
      contents += scrollPane
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
    listenTo(button)
    var nClicks = 0
    reactions += {
      case ButtonClicked(b) =>
        nClicks += 1
        label.text = "Number of button clicks: " + nClicks
    }
  }
}