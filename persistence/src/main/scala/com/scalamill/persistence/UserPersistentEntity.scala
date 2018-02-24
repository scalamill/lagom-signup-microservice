package com.scalamill.persistence

import java.time.LocalDateTime

import com.lightbend.lagom.scaladsl.persistence._
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}

import scala.collection.immutable.Seq

class UserPersistenceEntity extends PersistentEntity {

  override type Command = CustomCommand
  override type Event = SignUpEvent
  override type State = UserState

  override def initialState = UserState(None, LocalDateTime.now.toString)

  override def behavior: Behavior = Actions().onCommand[SignUpCommand, UserSignUpDone] {
    case (SignUpCommand(user), ctx, state) =>
      ctx.thenPersist(SignUpEvent(user, user.name)) {
        _ => ctx.reply(UserSignUpDone(user.name))
      }
  }.onEvent { case (SignUpEvent(user, userEntityId), state) =>
//    println(state)
    UserState(Some(user), LocalDateTime.now.toString)
  }.onReadOnlyCommand[SignInCommand, Boolean] {
    case (SignInCommand(user), ctx, state) => ctx.reply(state.user.getOrElse(User("", "")) == user)
  }
}

object UserPersistenceSerializationRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[UserSignUpDone],
    JsonSerializer[SignUpCommand]
  )
}