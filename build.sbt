name := "akka-http-scalajs.g8"

enablePlugins(ScriptedPlugin)

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.5",
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",
  "org.scala-js" %%% "scalajs-dom" % "1.1.0"
)
