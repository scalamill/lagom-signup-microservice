package com.scalamill.signup.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.scalamill.signup.api.SignUpLagomService
import play.api.libs.ws.ahc.AhcWSComponents

/**
  * Created by laxmi on 14/2/18.
  */
class SignUpServiceLoader extends LagomApplicationLoader {

    override def load(context: LagomApplicationContext): LagomApplication =
      new SignUpLagomApplication(context) {
        override def serviceLocator: ServiceLocator = NoServiceLocator
      }

    override def loadDevMode(context: LagomApplicationContext): LagomApplication =
      new SignUpLagomApplication(context) with LagomDevModeComponents

    override def describeService = Some(readDescriptor[SignUpLagomService])

}

abstract class SignUpLagomApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents {

  import com.softwaremill.macwire._

  override lazy val lagomServer = serverFor[SignUpLagomService](wire[SignUpLagomServiceImpl])



}