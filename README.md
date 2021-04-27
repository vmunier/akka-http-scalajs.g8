# Akka HTTP with Scala.js

[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

This is a [Giter8](http://www.foundweekends.org/giter8/) template showing how you can integrate an Akka HTTP project with a Scala.js project.

## Run the application

```shell
$ sbt new vmunier/akka-http-scalajs.g8
$ cd akka-http-scalajs
$ sbt
sbt:root> project server
sbt:server> ~reStart
$ open http://localhost:8080
```

The application contains three directories:
* `server` Akka HTTP application (server side)
* `client` Scala.js application (client side)
* `shared` Scala code that you want to share between the server and the client

## Features

The application uses the [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs) sbt plugin and the [scalajs-scripts](https://github.com/vmunier/scalajs-scripts) library.

- `compile`, `run`, `reStart` trigger the Scala.js `fastOptJS` task
- `~compile`, `~run`, `~reStart` continuous compilation is also available
- Set `scalaJSStage` to `FullOptStage` when packaging your application for `fullOptJS` to be executed instead of `fastOptJS`:
  ```
  sbt 'set Global / scalaJSStage := FullOptStage' universal:packageBin
  ```
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown.
  - Source Maps are enabled in both `fastOptJS` and `fullOptJS` by default. If you wish to disable Source Maps in `fullOptJS`, then add `Compile / fullOptJS / scalaJSLinkerConfig ~= (_.withSourceMap(false))` in the Scala.js projects.

## Cleaning

The `root` project aggregates all the other projects. Use this root project to clean all the projects at once.
```shell
$ sbt
sbt:root> clean
```

## Load the server project at sbt startup

Add the following line to `build.sbt` if you wish to load the server project at sbt startup:
```scala
Global / onLoad := (Global / onLoad).value.andThen(state => "project server" :: state)
```

`clean` will only delete the server's generated files (in the `server/target` directory). Call `root/clean` to delete the generated files for all the projects.

## IDE integration

### IntelliJ

In IntelliJ, open Project wizard, select `Import Project`, choose the root folder and click `OK`.
Select `Import project from external model` option, choose `SBT project` and click `Next`. Select additional import options and click `Finish`.
Make sure you use the IntelliJ Scala Plugin v2017.2.7 or higher. There are known issues with prior versions of the plugin.

### Eclipse

1. Add `addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.4")` to `project/plugins.sbt`
2. Add the following lines to the `server`'s settings in `build.sbt`:
```
// Compile the project before generating Eclipse files, so that generated .scala or .class files for Twirl templates are present
EclipseKeys.preTasks := Seq(Compile / compile)
```
3. Run `$ sbt "eclipse with-source=true"`
4. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder. Uncheck the third checkbox to only import client, server and shared/.jvm, click `Finish`. ![Alt text](screenshots/eclipse-akka-http-scalajs.png?raw=true "eclipse akka-http-scalajs screenshot")

## Classpath during development

The assets (js files, sourcemaps, etc.) are added to the classpath during development thanks to the following lines:
```
Assets / WebKeys.packagePrefix := "public/",
Runtime / managedClasspath += (Assets / packageBin).value
```

Note that `Assets / packageBin` also executes any tasks appended to `pipelineStages`, e.g. `gzip`.
You may want to avoid executing tasks under `pipelineStages` during development, because it could take long to execute.

In that case, in order to still have access to the assets under `Assets / WebKeys.packagePrefix` during development, you can use the following code instead:
```
lazy val server = project.settings(
...
Assets / WebKeys.packagePrefix := "public/",
Assets / WebKeys.exportedMappings ++= (for ((file, path) <- (Assets / mappings).value)
  yield file -> ((Assets / WebKeys.packagePrefix).value + path)),
...
)
```
