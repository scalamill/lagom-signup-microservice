package com.scalamill.signup.impl

class SignUpLaogmPersistentEntity extends PersistentEntity {

    override type command = CustomCommand[_]
    override type Event = SignUpEvent
    override type State = SignUpState

    override def initialState = SignUpState(User("", ""))

    override def behaviour : Behaviour =  {
         
         case SignUpState(user) =>  Actions().onCommand[UseGreetingMessage, Done] {

      // Command handler for the UseGreetingMessage command
      case (UseGreetingMessage(newMessage), ctx, state) =>
        // In response to this command, we want to first persist it as a
        // GreetingMessageChanged event
        ctx.thenPersist(
          GreetingMessageChanged(newMessage)
        ) { _ =>
          // Then once the event is successfully persisted, we respond with done.
          ctx.reply(Done)
        }

    }

    }

}

case class SignUpState(user:User)

case class SignUpCommand extends ReplyType[Done] {
    user: User
}

case class UserCurrentState extends ReplyType[Option[User]]
