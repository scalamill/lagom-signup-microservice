package com.scalamill.signup.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader}
import com.scalamill.persistence.{UserPersistenceEntity, UserPersistenceSerializationRegistry}
import com.scalamill.signup.api.SignUpLagomService
import play.api.libs.ws.ahc.AhcWSComponents

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
  extends LagomApplication(context) with AhcWSComponents with CassandraPersistenceComponents{

  import com.softwaremill.macwire._

  override lazy val lagomServer = serverFor[SignUpLagomService](wire[SignUpLagomServiceImpl])

  override lazy val jsonSerializerRegistry = UserPersistenceSerializationRegistry

  persistentEntityRegistry.register(wire[UserPersistenceEntity])


}