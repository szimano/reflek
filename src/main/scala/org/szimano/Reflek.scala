package org.szimano

import scala.swing._
import event.ButtonClicked

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
    val initial = Array(
      Array("John", "Doe", "26"),
      Array("Jim", "Smith", "55"),
      Array("Robert", "Lang", "43"))

    val names = Array("First name", "Last name", "Age")

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