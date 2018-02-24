package com.scalamill.signin.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.scalamill.persistence.{SignInCommand, User, UserPersistenceEntity}
import com.scalamill.signin.api.SignInLagomService

class SignInLagomServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends
  SignInLagomService {
  /**
    * Example: curl http://localhost:9000/api/signin/admin/admin
    */
  override def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean] = ServiceCall {
    request =>
      val ref = persistentEntityRegistry.refFor[UserPersistenceEntity](name)
      ref.ask(SignInCommand(User(name, password)))
  }
}
