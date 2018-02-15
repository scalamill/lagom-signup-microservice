package com.scalamill.signin.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.scalamill.signin.api.SignInLagomService
import com.scalamill.signup.api.{User, UsersService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by laxmi on 14/2/18.
  */
class SignInLagomServiceImpl extends SignInLagomService {
  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */
  override def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean] = ServiceCall {

    request => Future(UsersService.userExists(User(name, password)))

  }
}
