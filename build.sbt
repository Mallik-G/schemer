import Dependencies._

val libVersion = sys.env.get("TRAVIS_TAG") orElse sys.env.get("BUILD_LABEL") getOrElse s"1.0.0-${System.currentTimeMillis / 1000}-SNAPSHOT"

lazy val schemer = Project(
  id = "schemer",
  base = file(".")
) aggregate (core, registry)

lazy val core = (project in file("schemer-core")).settings(
  inThisBuild(
    List(
      organization := "com.indix",
      scalaVersion := "2.11.11",
      crossScalaVersions := Seq("2.11.11"),
      version := libVersion,
      scalafmtOnCompile := true
    )
  ),
  name := "schemer-core",
  libraryDependencies ++= Seq(sparkCore, sparkSql, sparkAvro, jsonSchemaValidator, scalaTest)
)

lazy val registry = (project in file("schemer-registry")).settings(
  inThisBuild(
    List(
      organization := "com.indix",
      scalaVersion := "2.11.11",
      version := libVersion,
      scalafmtOnCompile := true
    )
  ),
  name := "schemer-registry",
  libraryDependencies ++= akkaStack ++ loggingStack ++ Seq(sangria, sangriaSpray, scalaTest)
) dependsOn core