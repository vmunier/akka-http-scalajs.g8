// needed for the tests (ci/checksourcemaps.sh)
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8" % "0.13.1")
// needed to make sbt-giter8 work with SBT >= v1.2.x
libraryDependencies += { "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value }

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.2.0")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.10.0")
addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.9.1")
addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "1.1.1")
addSbtPlugin("com.github.sbt"            % "sbt-native-packager"       % "1.9.7")
addSbtPlugin("com.typesafe.sbt"          % "sbt-twirl"                 % "1.5.1")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.3.1")
