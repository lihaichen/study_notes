name := "first"
scalaVersion := "2.12.10"
val AkkaVersion = "2.6.7"
libraryDependencies += "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion
libraryDependencies += "org.specs2" %% "specs2-core" % "4.6.0" % "test"

