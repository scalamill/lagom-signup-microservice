package com.scalamill.signup.api

import play.api.libs.json.{Format, Json}
/**
  * user service to store users and check if they exists against provided username and password
  */

object UsersService {

  import scala.collection.mutable.ListBuffer

  val list = ListBuffer[User]()

  def addUser(user: User): Boolean = {
    list += user
    userExists(user)
  }

  def userExists(user: User): Boolean = {
    println(list)
    list.exists(_ == user)
  }

}

case class User(name: String, password: String)

object User {
  implicit val format: Format[User] = Json.format[User]
}
