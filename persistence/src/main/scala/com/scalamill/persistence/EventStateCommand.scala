package com.scalamill.persistence

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag}
import play.api.libs.json.{Format, Json}

sealed trait CustomCommand

case class SignInCommand(user: User) extends CustomCommand with ReplyType[Boolean]

object SignInCommand {

  implicit val format: Format[SignInCommand] = Json.format

}

case class SignUpCommand(user: User) extends CustomCommand with ReplyType[UserSignUpDone]

object SignUpCommand {

  implicit val format: Format[SignUpCommand] = Json.format

}

case class UserSignUpDone(userId: String)

object UserSignUpDone {

  implicit val format: Format[UserSignUpDone] = Json.format

}

object SignUpEvent {
  val Tag = AggregateEventTag[SignUpEvent]
}

case class SignUpEvent(user: User, userEntityId: String) extends AggregateEvent[SignUpEvent] {

  override def aggregateTag: AggregateEventTag[SignUpEvent] = SignUpEvent.Tag
}


case class UserState(user: Option[User], timeStamp: String)

case class User(name: String, password: String)

object User {
  implicit val format: Format[User] = Json.format[User]
}
