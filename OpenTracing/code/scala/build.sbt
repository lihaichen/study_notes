name := "opentracing"

version := "0.1"

scalaVersion := "2.12.10"
libraryDependencies += "org.specs2" %% "specs2-core" % "4.6.0" % "test"
libraryDependencies += "io.jaegertracing" % "jaeger-client" % "1.4.0"
//libraryDependencies += "org.apache.skywalking" % "apm-toolkit-opentracing" % "8.5.0" % "provided"
//libraryDependencies += "org.apache.skywalking" % "apm-toolkit-trace" % "8.5.0"