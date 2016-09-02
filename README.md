# Akka HTTP with Scala.js

[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

This is a simple example application showing how you can integrate an Akka HTTP project with a Scala.js project.

The application contains three directories:
* `server` Akka HTTP application (server side)
* `client` Scala.js application (client side)
* `shared` Scala code that you want to share between the server and the client

## Run the application
```shell
$ sbt
> ~re-start
$ open http://0.0.0.0:8080
```

## Features

The application uses the [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs) sbt plugin and the [scalajs-scripts](https://github.com/vmunier/scalajs-scripts) library.

- `compile`, `run`, `re-start` trigger the Scala.js fastOptJS command
- `~compile`, `~run`, `~re-start` continuous compilation is also available
- Production archives (e.g. using `assembly`, `universal:packageBin`) contain the optimised javascript
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown
  - Source Maps is _disabled in production_ by default to prevent your users from seeing the source files. But it can easily be enabled in production too by setting `emitSourceMaps in fullOptJS := true` in the Scala.js projects.

## Cleaning

The root project aggregates all the other projects by default.
Use this root project, called `akka-http-with-scalajs-example`, to clean all the projects at once.
```shell
$ sbt
> akka-http-with-scalajs-example/clean
```

## IDE integration

### Eclipse

1. `$ sbt "eclipse with-source=true"`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder. Uncheck the first and the last checkboxes to only import client, server and one shared, click `Finish`. ![Alt text](screenshots/eclipse-akka-http-with-scalajs-example.png?raw=true "eclipse akka-http-with-scalajs-example screenshot")

### IntelliJ

In IntelliJ, open Project wizard, select `Import Project`, choose the root folder and click `OK`.
Select `Import project from external model` option, choose `SBT project` and click `Next`. Select additional import options and click `Finish`.
Make sure you use the IntelliJ Scala Plugin v1.3.3 or higher. There are known issues with prior versions of the plugin.

## Classpath during development

The assets (js files, sourcemaps, etc.) are added to the classpath during development thanks to the following lines:
```
WebKeys.packagePrefix in Assets := "public/",
managedClasspath in Runtime += (packageBin in Assets).value
```

Note that `packageBin in Assets` also executes any tasks appended to `pipelineStages`, e.g. `gzip`.
You may want to avoid executing tasks under `pipelineStages` during development, because it could take long to execute.

In that case, in order to still have access to the assets under `WebKeys.packagePrefix in Assets` during development, you can use the following code instead:
```
lazy val server = (project in file("server")).settings(
...
WebKeys.packagePrefix in Assets := "public/"
WebKeys.exportedMappings in Assets ++= replacePathPrefix(
    (WebKeys.exportedMappings in Assets).value,
    SbtWeb.webJarsPathPrefix.value,
    (WebKeys.packagePrefix in Assets).value)
...
)

def replacePathPrefix(mappings: Seq[PathMapping], fromPrefix: String, toPrefix: String) = {
  for ((file, path) <- mappings) yield {
    val updatedPath = if (path.startsWith(fromPrefix)) {
      path.replaceFirst(fromPrefix, toPrefix)
    } else path
    file -> updatedPath
  }
}
```
