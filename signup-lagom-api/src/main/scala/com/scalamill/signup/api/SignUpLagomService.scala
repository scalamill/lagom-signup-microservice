package com.scalamill.signup.api

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

  def signUp: ServiceCall[User, UserSignUpDone]

  override final def descriptor = {
    import Service._
    named("signup").withCalls(
      restCall(Method.POST, "/api/signup/", signUp)
    ).withAutoAcl(true)
  }
}

