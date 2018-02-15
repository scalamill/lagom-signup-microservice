package com.scalamill.signup.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The signup-lagom service interface.
  * This describes everything that Lagom needs to know about how to serve and
  * consume the SignUpLagomService.
  */
trait SignUpLagomService extends Service {

  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */

  def signUp(user: User): ServiceCall[User, Boolean]

  override final def descriptor = {
    import Service._
    named("orders").withCalls(
      restCall(Method.POST,   "/api/signup/item", signUp _)
    ).withAutoAcl(true)
  }
}

