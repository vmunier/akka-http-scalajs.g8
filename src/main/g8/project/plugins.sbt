// Use Scala.js 0.6.x
addSbtPlugin("com.vmunier"                  % "sbt-web-scalajs"           % "1.0.11-0.6")
addSbtPlugin("org.scala-js"                 % "sbt-scalajs"               % "0.6.32")
// If you prefer using Scala.js 1.x, uncomment the following plugins instead:
// addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.11")
// addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.0.0-RC2")

// fast development turnaround when using sbt ~reStart
addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.9.1")
addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "0.14.10")
addSbtPlugin("com.typesafe.sbt"          % "sbt-native-packager"       % "1.6.1")
addSbtPlugin("com.typesafe.sbteclipse"   % "sbteclipse-plugin"         % "5.2.4")
addSbtPlugin("com.typesafe.sbt"          % "sbt-twirl"                 % "1.5.0")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "0.6.1")
