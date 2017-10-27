# Akka HTTP with Scala.js

[![License](http://img.shields.io/:license-Apache%202-red.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

This is a [Giter8](http://www.foundweekends.org/giter8/) template showing how you can integrate an Akka HTTP project with a Scala.js project.

## Run the application

```shell
$ sbt new vmunier/akka-http-scalajs.g8 --name=akka-http-scalajs --organization=com.example
$ cd akka-http-scalajs
$ sbt
> ~reStart
$ open http://localhost:8080
```

The application contains three directories:
* `server` Akka HTTP application (server side)
* `client` Scala.js application (client side)
* `shared` Scala code that you want to share between the server and the client

## Features

The application uses the [sbt-web-scalajs](https://github.com/vmunier/sbt-web-scalajs) sbt plugin and the [scalajs-scripts](https://github.com/vmunier/scalajs-scripts) library.

- `compile`, `run`, `reStart` trigger the Scala.js fastOptJS command
- `~compile`, `~run`, `~reStart` continuous compilation is also available
- Production archives (e.g. using `assembly`, `universal:packageBin`) contain the optimised javascript
- Source maps
  - Open your browser dev tool to set breakpoints or to see the guilty line of code when an exception is thrown
  - Source Maps is _disabled in production_ by default to prevent your users from seeing the source files. But it can easily be enabled in production too by setting `emitSourceMaps in fullOptJS := true` in the Scala.js projects.

## Cleaning

The root project aggregates all the other projects by default.
Use this root project, called `akka-http-scalajs`, to clean all the projects at once.
```shell
$ sbt
> akka-http-scalajs/clean
```

## IDE integration

### IntelliJ

In IntelliJ, open Project wizard, select `Import Project`, choose the root folder and click `OK`.
Select `Import project from external model` option, choose `SBT project` and click `Next`. Select additional import options and click `Finish`.
Make sure you use the IntelliJ Scala Plugin v2017.2.7 or higher. There are known issues with prior versions of the plugin.

### Eclipse

1. `$ sbt "eclipse with-source=true"`
2. Inside Eclipse, `File/Import/General/Existing project...`, choose the root folder. Uncheck the third checkbox to only import client, server and shared/.jvm, click `Finish`. ![Alt text](screenshots/eclipse-akka-http-scalajs.png?raw=true "eclipse akka-http-scalajs screenshot")


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
WebKeys.packagePrefix in Assets := "public/",
WebKeys.exportedMappings in Assets ++= (for ((file, path) <- (mappings in Assets).value)
  yield file -> ((WebKeys.packagePrefix in Assets).value + path)),
...
)
```
