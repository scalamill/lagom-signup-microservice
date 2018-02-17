package com.scalamill.signup.impl

import java.time.LocalDateTime

import com.lightbend.lagom.scaladsl.persistence.PersistentEntity.ReplyType
import com.lightbend.lagom.scaladsl.persistence._
import com.lightbend.lagom.scaladsl.playjson.{JsonSerializer, JsonSerializerRegistry}
import com.scalamill.signup.api._
import play.api.libs.json.{Format, Json, OFormat}

import scala.collection.immutable.Seq

class SignUpLaogmPersistentEntity extends PersistentEntity {

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
    println("show current state" + user)
    UserState(Some(user), LocalDateTime.now.toString)
  }
}





object HellolagomSerializerRegistry extends JsonSerializerRegistry {
  override def serializers: Seq[JsonSerializer[_]] = Seq(
    JsonSerializer[UserSignUpDone],
    JsonSerializer[SignUpCommand]
    )
}