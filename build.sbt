import sbt._
import sbt.Keys._

ThisBuild / cloudflowDockerRegistry := None
ThisBuild / cloudflowDockerRepository := Some("localhost:5000")

lazy val root =
  Project(id = "root", base = file("."))
    .settings(
      name := "root",
      skip in publish := true,
    )
    .withId("root")
    .settings(commonSettings)
    .aggregate(
      dataModel,
      sharedStreamlets,
      app1,
      app2
    )

lazy val dataModel = (project in file("./datamodel"))
  .enablePlugins(CloudflowLibraryPlugin)
  .settings(
    commonSettings
  )
  .settings(
    avroSettings
  )

// no own container both app1 & app2 will contain it -> random resolution in app*.json
lazy val sharedStreamlets = (project in file("./sharedstreamlets"))
  //.enablePlugins(CloudflowLibraryPlugin)
  .settings(
    commonSettings
  )
  .settings(
    libraryDependencies ++= Seq(Dependencies.CloudflowAkkaUtil)
  )
  .dependsOn(dataModel)

/*
// own container -> streamlet comes from this
lazy val sharedStreamlets = (project in file("./sharedstreamlets"))
  .enablePlugins(CloudflowAkkaPlugin)
  .settings(
    commonSettings
  )
  .dependsOn(dataModel)
*/

lazy val app1 = (project in file("./app1"))
  .enablePlugins(CloudflowAkkaPlugin)
  .enablePlugins(CloudflowApplicationPlugin)
  .settings(
    commonSettings
  ).dependsOn(sharedStreamlets)

lazy val app2 = (project in file("./app2"))
  .enablePlugins(CloudflowAkkaPlugin)
  .enablePlugins(CloudflowApplicationPlugin)
  .settings(
    commonSettings
  ).dependsOn(sharedStreamlets)



lazy val commonSettings = Seq(
  scalaVersion := "2.12.11",
  scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-target:jvm-1.8",
    "-Xlog-reflective-calls",
    "-Xlint",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
    "-deprecation",
    "-feature",
    "-language:_",
    "-unchecked"
  ),

  scalacOptions in (Compile, console) --= Seq("-Ywarn-unused", "-Ywarn-unused-import"),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value

)

import sbtavrohugger.SbtAvrohugger.autoImport.{avroScalaGenerateSpecific, avroSourceDirectories ,avroSpecificScalaSource}
lazy val avroSettings = Seq(
  // FIXME - need to manually define avro and potential protobuf managed source folders (where code is generated to)
  Compile / managedSourceDirectories += (Compile / avroSpecificScalaSource).value,
  // FIXME - needs to be hardcoded since no key is available for java sources
  Compile / managedSourceDirectories += (Compile / crossTarget).value / "java_avro",
  Compile / managedSourceDirectories += (Compile / crossTarget).value / "scalapb",
  Compile / managedSourceDirectories += (Compile / crossTarget).value / "akka-grpc" / "main",

  watchSources ++= ((Compile / avroSourceDirectories).value ** "*.avsc").get
)
