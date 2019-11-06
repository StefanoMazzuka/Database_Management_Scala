package Model

import java.sql.{Connection, DriverManager, Statement}

import View.ErrorWindow

/**
 * Created on 04/11/2019
 * Time 14:49H
 *
 * @author Stefano Mazzuka
 */
object Connection {
  val url = "jdbc:mysql://localhost:3306/scala_db"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = ""
  var connection: Connection = _
  var statement: Statement = null

  /**
   * Connection to database
   */
  def connect(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      statement = connection.createStatement
    } catch {
      case e: Exception => e.printStackTrace
        new ErrorWindow("Connection Error").visible = true
    }
  }

  /**
   * Close database connection
   */
  def close(): Unit = {
    try {
      connection.close
    } catch {
      case e: Exception => e.printStackTrace
    }
  }

  /**
   * Get all the users from the database
   * @return Array[Array[Any]] of users
   */
  def getUsers(): Array[Array[Any]] = {
    connect()

    val rs = statement.executeQuery("SELECT * FROM users")

    var rows = 0
    while (rs.next) {
      rows += 1
    }

    val users = Array.ofDim[Any](rows, 3)
    rs.beforeFirst()
    var row = 0
    while (rs.next) {
      users(row)(0) = rs.getString("id")
      users(row)(1) = rs.getString("user_name")
      users(row)(2) = rs.getString("user_password")
      row += 1
    }

    close()
    users
  }

  /**
   * Insert a new user to the database
   * @param user_name Name for the new user
   * @param user_password Password for the new user
   */
  def addUser(user_name: String, user_password: String): Unit = {
    connect()

    val sql = "INSERT INTO `users` (`user_name`, `user_password`) VALUES ('" + user_name + "', '" + user_password + "');"
    connection.prepareStatement(sql).executeUpdate()

    close()
  }

  /**
   * Delete one user
   * @param id Id from the user to delete
   */
  def deleteUser(id: String): Unit = {
    connect()

    val sql = "DELETE FROM `users` WHERE `id` = '" + id + "';"
    connection.prepareStatement(sql).executeUpdate()

    close()
  }
}
