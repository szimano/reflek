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

    var initial = leki.map(lek => Array[AnyRef](lek.substancja, lek.nazwa, lek.opakowanie, lek.grupa, lek.wskazania,
      lek.refundacja, lek.cena))

    val names = Array[AnyRef]("Substancja", "Nazwa i postać", "Opakowanie", "Grupa", "Zakres", "Refundacja", "Cena")

    val tableModel = new DefaultTableModel( initial, names ) {
      override def isCellEditable(x: Int, y: Int) = false
    }

    val memTable = new Table(1, 2) {
      model = tableModel
      selection.intervalMode = Table.IntervalMode.Single
    }

    val scrollPane = new ScrollPane(memTable)

    val substancja = new Label() {
      text = "Substancja:"
    }
    val nazwa = new Label() {
      text = "Nazwa i postać:"
    }
    val opakowanie = new Label() {
      text = "Opakowanie:"
    }
    val grupa = new Label() {
      text = "Grupa:"
    }
    val zakres = new Label() {
      text = "Zakres:"
    }
    zakres.horizontalTextPosition = Alignment.Left
    zakres.horizontalAlignment = Alignment.Left

    val refundacja = new Label() {
      text = "Refundacja:"
    }

    val cena = new Label() {
      text = "Cena:"
    }

    val opisLeku = new BoxPanel(Orientation.Vertical) {
      contents += substancja
      contents += nazwa
      contents += opakowanie
      contents += grupa
      contents += zakres
      contents += refundacja
      contents += cena
      xLayoutAlignment = java.awt.Component.LEFT_ALIGNMENT
    }

    contents = new BoxPanel(Orientation.Vertical) {
      contents += label
      contents += search
      contents += button
      contents += scrollPane
      contents += opisLeku
      border = Swing.EmptyBorder(30, 30, 10, 30)
    }
    listenTo(search)
    listenTo(button)
    listenTo(memTable.selection)
    listenTo(search.keys)
    listenTo(memTable.mouse.moves)
    listenTo(search.mouse.moves)

    reactions += {
      case ValueChanged(v) => {
        val searchString = v.asInstanceOf[TextField].text;
        val lekiWyszukane = searchLeki("%"+searchString.toLowerCase+"%")

        initial = lekiWyszukane.map(lek => Array[AnyRef](lek.substancja, lek.nazwa, lek.opakowanie, lek.grupa,
          lek.wskazania, lek.refundacja, lek.cena))

        tableModel.setDataVector(initial, names)

        memTable.repaint()
        if (lekiWyszukane.size > 0) {
          memTable.selection.rows += (0, 0)
          reloadDesc(0)
        }
      }
      case ButtonClicked(b) =>
        search.text = ""
      case TableRowsSelected(table, range, adjusting) => {
        if (!memTable.selection.cells.isEmpty) {
          val row = memTable.selection.cells.head._1;

          reloadDesc(row)
        }
      }
      case KeyPressed(component, key, modifier, location) => {
        if(key.id == 27) {
          search.text = ""
        }
      }
      case MouseEntered(source, point, modifiers) => {
        source.requestFocus()
      }
    }
    
    def reloadDesc(row : Int) {
      if (row >= 0) {
        substancja.text = "Substancja: " + tableModel.getValueAt (row, 0)
        nazwa.text = "Nazwa i postać: " + tableModel.getValueAt (row, 1)
        opakowanie.text = "Opakowanie: " + tableModel.getValueAt (row, 2)
        grupa.text = "Grupa: " + tableModel.getValueAt (row, 3)
        zakres.text = "<html>Zakres: " + tableModel.getValueAt (row, 4) + "</html>"
        refundacja.text = "Refundacja: " + tableModel.getValueAt (row, 5)
        cena.text = "Cena: " + tableModel.getValueAt (row, 6)
      }
    }
  }
}