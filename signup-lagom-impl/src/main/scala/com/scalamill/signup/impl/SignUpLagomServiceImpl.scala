package com.scalamill.signup.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.scalamill.persistence._
import com.scalamill.signup.api.SignUpLagomService

class SignUpLagomServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends SignUpLagomService {
  /**
    * Example: curl http://localhost:9000/api/signin/admin/admin
    * Example: curl -X POST   http://localhost:9000/api/signup/ -H 'content-type: application/json' -d '{"name":"admin", "password":"admin"}'
    */
  override def signUp: ServiceCall[User, UserSignUpDone] = ServiceCall {
    user =>
      val ref = persistentEntityRegistry.refFor[UserPersistenceEntity](user.name)
      ref.ask(SignUpCommand(user))
  }

  override def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean] = ServiceCall {
    request =>
      val ref = persistentEntityRegistry.refFor[UserPersistenceEntity](name)
      ref.ask(SignInCommand(User(name, password)))
  }
}
