import Dependencies._

val osName: SettingKey[String] = SettingKey[String]("osName")

osName := (System.getProperty("os.name") match {
  case name if name.startsWith("Linux") => "linux"
  case name if name.startsWith("Mac") => "mac"
  case name if name.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
})

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
      "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
      "net.databinder.dispatch" %% "dispatch-core" % "0.13.3",
      "javax.xml.bind" % "jaxb-api" % "2.3.1",
      // END: Scalaxb dependency
      // Slick framework dependency
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      // END: Slick framework dependency
      "org.postgresql" % "postgresql" % "42.1.4",  // PG Dependency
      "com.github.scopt" %% "scopt" % "3.7.0",  // Option parsing
      "org.json4s" %% "json4s-native" % "3.6.5",  // JSON parsing
      // OpenJFX UI
      "org.openjfx" % "javafx-base" % "11.0.2" classifier osName.value,
      "org.openjfx" % "javafx-controls" % "11.0.2" classifier osName.value,
      "org.openjfx" % "javafx-fxml" % "11.0.2" classifier osName.value,
      "org.openjfx" % "javafx-graphics" % "11.0.2" classifier osName.value,
      "org.openjfx" % "javafx-web" % "11.0.2" classifier osName.value,
      // END: OpenJFX UI
    ),
    mainClass in (Compile, run) := Some("runner.GUI"),  // Main class to start
    mainClass in assembly := Some("runner.GUI"),
    scalaxbPackageName in (Compile, scalaxb) := "generated",
    scalacOptions := Seq("-unchecked", "-deprecation")
  )
