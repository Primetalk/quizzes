val derby = "org.apache.derby" % "derby" % "10.4.1.3"
val scalaCheck = "org.scalacheck" %% "scalacheck" % "1.12.5" % "test"
val scalaTest = "org.scalatest" %% "scalatest" % "2.2.5" % "test"
val spire = "org.spire-math" %% "spire"  % "0.11.0"
val junit = "junit" % "junit" % "4.12"

lazy val commonSettings = Seq(
  version := "1.0",
  scalaVersion := "2.11.7"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "lines",
    libraryDependencies ++= Seq(
      scalaCheck,
//      scalaTest, junit,
      spire
    )
  )

