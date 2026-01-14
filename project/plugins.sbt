// needed for the tests (ci/checksourcemaps.sh)
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8" % "0.16.2")

// add the below dependencies in the template build, so that Scala Steward can update versions in the giter8 template
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.3.0")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.19.0")
addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.10.0")
addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "2.3.1")
addSbtPlugin("com.github.sbt"            % "sbt-native-packager"       % "1.11.7")
addSbtPlugin("org.playframework.twirl"   % "sbt-twirl"                 % "2.0.7")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "1.3.2")
