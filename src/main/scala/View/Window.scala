package View

import Model.Connection

import scala.swing._
import scala.swing.event.ButtonClicked

/**
 * Created on 05/11/2019
 * Time 9:17H
 *
 * @author Stefano Mazzuka
 */
class Window extends MainFrame {

  title = "Scala Database"

  preferredSize = new Dimension(300, 150)

  centerOnScreen()

  var table = loadTable()
  val scrollPane = new ScrollPane {
    contents = table
  }

  val btnAdd = new Button("Add")
  val btnDelete = new Button("Delete")
  val btnPanel = new GridPanel(1, 2) {
    contents += btnAdd
    contents += btnDelete
  }

  contents = new BorderPanel {
    add(scrollPane, BorderPanel.Position.Center)
    add(btnPanel, BorderPanel.Position.South)
  }

  listenTo(btnAdd)
  listenTo(btnDelete)
  reactions += {
    case ButtonClicked(b)
      if b == btnAdd =>
      new AddUser().visible = true
      dispose
    case ButtonClicked(b)
      if b == btnDelete =>
      if (table.selection.rows.leadIndex != -1) {
        val id = table.apply(table.selection.rows.leadIndex, 0).toString
        Connection.deleteUser(id)
        new Window().visible = true
        dispose
      }
  }

  def loadTable(): Table = {
    val model = Connection.getUsers()
    new Table(model, Array("id", "Username", "Password"))
  }

  override def closeOperation(): Unit = dispose
}