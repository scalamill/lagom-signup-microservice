package com.scalamill.signin.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}

trait SignInLagomService extends Service {

  /**
    * Example: curl http://localhost:9000/api/signin/admin/admin
    */

  def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean]

  override final def descriptor = {
    import Service._
    named("signin").withCalls(
      pathCall("/api/signin/:name/:password", signIn _)
    ).withAutoAcl(true)
  }
}

