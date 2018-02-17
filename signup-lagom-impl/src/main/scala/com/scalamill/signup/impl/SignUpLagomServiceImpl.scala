package com.scalamill.signup.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.scalamill.signup.api.{SignUpCommand, SignUpLagomService, UserSignUpDone, UsersService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import com.lightbend.lagom.scaladsl.persistence.{EventStreamElement, PersistentEntityRegistry}
/**
  * Created by laxmi on 14/2/18.
  */
class SignUpLagomServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends SignUpLagomService {
  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */
  import com.scalamill.signup.api.User

  override def signUp: ServiceCall[User, UserSignUpDone] = ServiceCall {

    request =>

      val ref = persistentEntityRegistry.refFor[SignUpLaogmPersistentEntity](request.name)

      ref.ask(SignUpCommand(request))

  }
}
