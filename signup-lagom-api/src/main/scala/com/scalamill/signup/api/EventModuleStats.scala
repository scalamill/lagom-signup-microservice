package com.scalamill.signup.api

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence.{AggregateEvent, AggregateEventTag}
import play.api.libs.json.{Format, Json}

case class UserState(user: Option[User], timeStamp: String)

object SignUpEvent {
  val Tag = AggregateEventTag[SignUpEvent]
}
case class SignUpEvent(user: User, userEntityId: String) extends AggregateEvent[SignUpEvent] {

  override def aggregateTag: AggregateEventTag[SignUpEvent] = SignUpEvent.Tag
}

case class SignUpCommand(user: User) extends CustomCommand with ReplyType[UserSignUpDone]

object SignUpCommand {

  implicit val format: Format[SignUpCommand] = Json.format

}

case class UserSignUpDone(userId: String)

object UserSignUpDone {

  implicit val format: Format[UserSignUpDone] = Json.format

}
sealed trait CustomCommand

case object UserCurrentState extends ReplyType[Option[User]]