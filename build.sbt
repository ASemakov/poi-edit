import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "man.asemakov",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "PoiEdit",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      // Slick framework dependency
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      // END: Slick framework dependency
      "org.postgresql" % "postgresql" % "42.1.4"  // PG Dependency
    ),
    mainClass in (Compile, run) := Some("example.Hello")
  )
