package org.szimano

import scala.swing._

import BazaLekow._
import event._
import javax.swing.table.DefaultTableModel

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

    var initial = new Array[Array[AnyRef]](leki.size)

    var i = 0;

    for (lek <- leki) {
      initial(i) = Array[AnyRef](lek.substancja, lek.nazwa, lek.opakowanie, lek.grupa, lek.wskazania, lek.refundacja)
      i = i + 1
    }
    
    val names = Array[AnyRef]("Substancja", "Nazwa i postać", "Opakowanie", "Grupa", "Zakres", "Refundacja")

    val tableModel = new DefaultTableModel( initial, names )

    var memTable = new Table(1, 2) {model = tableModel}


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
        val lekiWyszukane = searchLeki("%"+searchString.toLowerCase+"%")

        initial = new Array[Array[AnyRef]](lekiWyszukane.size)

        var i = 0;

        for (lek <- lekiWyszukane) {
          initial(i) = Array[AnyRef](lek.substancja, lek.nazwa, lek.opakowanie, lek.grupa, lek.wskazania, lek.refundacja)
          i = i + 1
        }

        tableModel.setDataVector(initial, names)

        memTable.repaint()
      }
      case ButtonClicked(b) =>
        search.text = ""
    }
    
  }
}