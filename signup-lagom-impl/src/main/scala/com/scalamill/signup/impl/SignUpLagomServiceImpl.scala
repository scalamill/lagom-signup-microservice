package com.scalamill.signup.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.scalamill.persistence.{SignUpCommand, User, UserPersistenceEntity, UserSignUpDone}
import com.scalamill.signup.api.SignUpLagomService

class SignUpLagomServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends SignUpLagomService {
  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */
  override def signUp: ServiceCall[User, UserSignUpDone] = ServiceCall {

    request =>

      val ref = persistentEntityRegistry.refFor[UserPersistenceEntity](request.name)

      ref.ask(SignUpCommand(request))

  }
}
