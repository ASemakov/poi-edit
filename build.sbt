import Dependencies._

lazy val root = (project in file(".")).
  enablePlugins(ScalaxbPlugin).
  settings(
    inThisBuild(List(
      organization := "man.asemakov",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "poi-edit",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.scala-lang" % "scala-reflect" % "2.12.3",
      // Scalaxb dependency
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.6",
      // END: Scalaxb dependency
      // Slick framework dependency
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      // END: Slick framework dependency
      "org.postgresql" % "postgresql" % "42.1.4",  // PG Dependency
      "com.github.scopt" %% "scopt" % "3.7.0",  // Option parsing
      "org.json4s" %% "json4s-native" % "3.5.3"  // JSON parsing
),
    mainClass in (Compile, run) := Some("runner.GUI"),  // Main class to start
    mainClass in assembly := Some("runner.GUI"),
    scalaxbPackageName in (Compile, scalaxb) := "scalaxb.generated",
    scalacOptions := Seq("-unchecked", "-deprecation")
  )
