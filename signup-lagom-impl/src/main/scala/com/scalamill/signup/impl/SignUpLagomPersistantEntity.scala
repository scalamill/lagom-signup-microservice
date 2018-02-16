package com.scalamill.signup.impl

import java.time.LocalDateTime

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence._
import com.scalamill.signup.api.User

class SignUpLaogmPersistentEntity extends PersistentEntity {

  override type Command = Command
  override type Event = SignUpEvent
  override type State = UserState

  override def initialState = UserState(None, LocalDateTime.now.toString)

  override def behavior: Behavior = Actions().onCommand[SignUpCommand, UserSignUpDone] {
    case (SignUpCommand(user), ctx, state) =>
      ctx.thenPersist(SignUpEvent(user, user.name)) {
        _ => ctx.reply(UserSignUpDone(user.name))
      }
  }.onEvent { case SignUpEvent(user, userEntityId) =>
    UserState(Some(user), LocalDateTime.now.toString)
  }
}

case class UserState(user: Option[User], timeStamp: String)


case class SignUpEvent(user: User, userEntityId: String) extends AggregateEvent[SignUpEvent] {
  val Tag = AggregateEventTag.sharded[SignUpEvent](20)

  override def aggregateTag: AggregateEventShards[SignUpEvent] = Tag
}

case class SignUpCommand(user: User) extends Command with ReplyType[UserSignUpDone]

case class UserSignUpDone(userId: String)

sealed trait Command

case object UserCurrentState extends ReplyType[Option[User]]
