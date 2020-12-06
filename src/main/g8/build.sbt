lazy val server = project
  .settings(commonSettings)
  .settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    // triggers scalaJSPipeline when using compile or continuous compilation
    compile in Compile := ((compile in Compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.2.1",
      "com.typesafe.akka" %% "akka-stream" % "2.6.10",
      "com.vmunier" %% "scalajs-scripts" % "1.1.4"
    ),
    WebKeys.packagePrefix in Assets := "public/",
    managedClasspath in Runtime += (packageBin in Assets).value
  )
  .enablePlugins(SbtWeb, SbtTwirl, JavaAppPackaging)
  .dependsOn(sharedJvm)

lazy val client = project
  .settings(commonSettings)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "1.1.0"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSWeb)
  .dependsOn(sharedJs)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(commonSettings)
  .jsConfigure(_.enablePlugins(ScalaJSWeb))
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  organization := "$organization$"
)
