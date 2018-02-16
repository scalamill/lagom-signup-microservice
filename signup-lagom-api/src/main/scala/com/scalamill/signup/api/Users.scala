package com.scalamill.signup.api

import play.api.libs.json.{Format, Json}
/**
  * user service to store users and check if they exists against provided username and password
  */

object UsersService {

  import scala.collection.mutable.Set

  val set = Set[User]()

  def addUser(user: User): Boolean = {
    set += user
    userExists(user)
  }

  def userExists(user: User): Boolean = {
    println(set)
    set.exists(_ == user)
  }

}

case class User(name: String, password: String)

object User {
  implicit val format: Format[User] = Json.format[User]
}
