ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "COMP443-Project3"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % Test
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"