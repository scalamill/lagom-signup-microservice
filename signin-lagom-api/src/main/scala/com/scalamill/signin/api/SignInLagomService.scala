package com.scalamill.signin.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

/**
  * The signup-lagom service interface.
  * This describes everything that Lagom needs to know about how to serve and
  * consume the SignUpLagomService.
  */
trait SignInLagomService extends Service {

  /**
    * Example: curl http://localhost:9000/api/signup/admin/admin
    */

  def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean]

  override final def descriptor = {
    import Service._
    named("signin").withCalls(
      pathCall("/api/signin/:name/:password", signIn _)
    ).withAutoAcl(true)
  }
}

