package org.szimano

import scala.swing._

import BazaLekow._
import event._

/**
 *
 * User: szimano
 */

object Reflek extends SimpleSwingApplication {
  def top = new MainFrame {
    title = "Reflek"

    val label = new Label {
      text = "Wyszukaj"
    }
    
    val button = new Button {
      text = "Wyczyść"
    }

    val search = new TextField()

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
      contents += search
      contents += button
      contents += scrollPane
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
    listenTo(search)
    listenTo(button)

    var nClicks = 0
    reactions += {
      case ValueChanged(v) => {
        val searchString = v.asInstanceOf[TextField].text;
        val lekiWyszukane = searchLeki(searchString)
        
        var i = 0
        for (lek <- lekiWyszukane) {
          println(lek)
          memTable.update(i, 0, lek.substancja)
          memTable.update(i, 1, lek.nazwa)
          memTable.update(i, 2, lek.opakowanie)
          memTable.update(i, 3, lek.grupa)
          memTable.update(i, 4, lek.wskazania)
          memTable.update(i, 5, lek.refundacja)

          i = i + 1
        }

        memTable.repaint()

        println("updated")
      }
      case ButtonClicked(b) =>
        search.text = ""
    }
    
  }
}