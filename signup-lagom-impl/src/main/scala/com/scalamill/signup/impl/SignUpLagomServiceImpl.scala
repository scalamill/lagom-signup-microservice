package com.scalamill.signup.impl

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.scalamill.signup.api.{SignUpLagomService, User, UsersService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by laxmi on 14/2/18.
  */
class SignUpLagomServiceImpl extends SignUpLagomService {
  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */
  override def signUp(user: User): ServiceCall[NotUsed, Boolean] = ServiceCall {

    request => Future(UsersService.addUser(user))

  }
}
