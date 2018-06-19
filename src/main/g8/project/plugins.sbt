$if(useScalaJS1x.truthy)$
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "1.0.0-M3")
$else$
addSbtPlugin("com.vmunier"               % "sbt-web-scalajs"           % "1.0.8-0.6")
addSbtPlugin("org.scala-js"              % "sbt-scalajs"               % "0.6.23")
$endif$

// fast development turnaround when using sbt ~reStart
addSbtPlugin("io.spray"                  % "sbt-revolver"              % "0.9.1")
addSbtPlugin("com.eed3si9n"              % "sbt-assembly"              % "0.14.7")
addSbtPlugin("com.typesafe.sbt"          % "sbt-native-packager"       % "1.3.5")
addSbtPlugin("com.typesafe.sbteclipse"   % "sbteclipse-plugin"         % "5.2.4")
addSbtPlugin("com.typesafe.sbt"          % "sbt-twirl"                 % "1.3.15")
addSbtPlugin("org.portable-scala"        % "sbt-scalajs-crossproject"  % "0.5.0")
