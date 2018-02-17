organization in ThisBuild := "com.scalamill"
version in ThisBuild := "1.0"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lazy val `signup-lagom` = (project in file("."))
  .aggregate(`signup-lagom-api`, `signup-lagom-impl`, `signin-lagom-api`, `signin-lagom-impl`)

lazy val `signup-lagom-api` = (project in file("signup-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslPersistenceCassandra
    )
  )

lazy val `signup-lagom-impl` = (project in file("signup-lagom-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      lagomScaladslPersistenceCassandra,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`signup-lagom-api`)

lazy val `signin-lagom-api` = (project in file("signin-lagom-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `signin-lagom-impl` = (project in file("signin-lagom-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`signin-lagom-api`, `signup-lagom-api`)


