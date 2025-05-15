name := "akka-http-scalajs.g8"

scalaVersion := "2.13.15"

enablePlugins(ScriptedPlugin, ScalaJSPlugin)

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.6.3",
  "com.typesafe.akka" %% "akka-stream" % "2.9.6",
  "com.vmunier" %% "scalajs-scripts" % "1.3.0",
  "org.scala-js" %%% "scalajs-dom" % "2.8.0"
)
