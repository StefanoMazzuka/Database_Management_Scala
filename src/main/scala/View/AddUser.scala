package View

import Model.Connection

import scala.swing._

/**
 * Created on 05/11/2019
 * Time 9:17H
 *
 * @author Stefano Mazzuka
 */
class AddUser extends MainFrame {

  preferredSize = new Dimension(300, 150)

  centerOnScreen()

  val user_name = new TextField("")
  val user_password = new TextField("")

  contents = new GridPanel(3, 2) {
    border = Swing.EmptyBorder(20)
    contents += new Label("User name:")
    contents += user_name
    contents += new Label("Password:")
    contents += user_password
    contents += new Label("")
    contents += Button("Add") {
      if (user_name.text != "" && user_password.text != "") {
        Connection.addUser(user_name.text, user_password.text)
        user_name.text = ""
        user_password.text = ""
        new Window().visible = true
        dispose
      }
    }
  }

  override def closeOperation(): Unit = dispose
}