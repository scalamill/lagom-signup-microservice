package com.scalamill.signin.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.scalamill.signin.api.SignInLagomService
import play.api.libs.ws.ahc.AhcWSComponents

class SignInServiceLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new SignInLagomApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new SignInLagomApplication(context) with LagomDevModeComponents

  override def describeService = Some(readDescriptor[SignInLagomService])

}

abstract class SignInLagomApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents {

  import com.softwaremill.macwire._

  override lazy val lagomServer = serverFor[SignInLagomService](wire[SignInLagomServiceImpl])


}