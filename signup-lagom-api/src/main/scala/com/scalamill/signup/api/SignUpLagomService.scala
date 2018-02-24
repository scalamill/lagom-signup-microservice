package com.scalamill.signup.api

import com.lightbend.lagom.scaladsl.api.transport.Method
import com.lightbend.lagom.scaladsl.api.{Service, ServiceCall}
import com.scalamill.persistence.{User, UserSignUpDone}

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

