package com.scalamill.signup.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.Service.pathCall
import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.scalamill.persistence.{User, UserSignUpDone}

trait SignUpLagomService extends Service {

  /**
    * Example: curl http://localhost:9000/api/signin/admin/admin
    * Example: curl -X POST   http://localhost:9000/api/signup/ -H 'content-type: application/json' -d '{"name":"admin", "password":"admin"}'
    */

  def signUp: ServiceCall[User, UserSignUpDone]
  def signIn(name: String, password: String): ServiceCall[NotUsed, Boolean]

  override final def descriptor = {
    import Service._
    named("signup").withCalls(
      restCall(Method.POST, "/api/signup/", signUp),
      pathCall("/api/signin/:name/:password", signIn _)
    ).withAutoAcl(true)
  }
}

