import sbt._

object Version {
  // Scala language extensions
  val ScalaCollectionCompat = "2.3.2"

  // External Libraries
  val Alpakka             = "2.0.2"
  val Akka                = "2.6.10"
  val AkkaHttp            = "10.2.0"

  val Cloudflow           = "2.0.21"
  val Logback             = "1.2.3"
  val SprayJson           = "1.3.5" // keep in sync with AkkaHttpJson

}

object Dependencies {
  val AkkaActor              = "com.typesafe.akka"           %% "akka-actor"                               % Version.Akka
  val AkkaActorTyped         = "com.typesafe.akka"           %% "akka-actor-typed"                         % Version.Akka
  val AkkaSlf4j              = "com.typesafe.akka"           %% "akka-slf4j"                               % Version.Akka
  val AkkaHttp               = "com.typesafe.akka"           %% "akka-http"                                % Version.AkkaHttp
  val AkkaHttp2Support       = "com.typesafe.akka"           %% "akka-http2-support"                       % Version.AkkaHttp
  val AkkaHttpSprayJson      = "com.typesafe.akka"           %% "akka-http-spray-json"                     % Version.AkkaHttp
  val AkkaHttpJackson        = "com.typesafe.akka"           %% "akka-http-jackson"                        % Version.AkkaHttp
  val AkkaStream             = "com.typesafe.akka"           %% "akka-stream"                              % Version.Akka

  val CloudflowAkka          = "com.lightbend.cloudflow"     %% "cloudflow-akka"                           % Version.Cloudflow
  val CloudflowAkkaUtil      = "com.lightbend.cloudflow"     %% "cloudflow-akka-util"                      % Version.Cloudflow

  val Logback                = "ch.qos.logback"               % "logback-classic"                          % Version.Logback

}