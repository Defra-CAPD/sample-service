import sbt.Keys._
import sbt._
import sbtassembly.AssemblyPlugin
import sbtassembly.AssemblyPlugin.autoImport._

object ApplicationBuild extends Build {
  lazy val apiDependencies = Seq.empty// Seq(
    //"io.dropwizard" % "dropwizard-core" % "0.7.1"
  //)

  lazy val serviceDependencies = Seq(
    "uk.gov.defra" % "sample-service-api" % "0.1.0",
    "io.dropwizard" % "dropwizard-core" % "0.7.1",
    "uk.gov.defra" % "capd-common" % "1.0.16"
  )

  lazy val testDependencies = Seq (
    "io.dropwizard" % "dropwizard-testing" % "0.7.1",
    "com.novocode" % "junit-interface" % "0.11" % "test"
  )

  lazy val clientDependencies = Seq (
    "uk.gov.defra" % "capd-jersey-client" % "0.5.8",
    "uk.gov.defra" % "sample-service-api" % "0.1.0",
    "io.dropwizard" % "dropwizard-core" % "0.7.1",
    "io.dropwizard" % "dropwizard-client" % "0.7.1"
  )

  lazy val appReleaseSettings = Seq(
    // Publishing options:
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomIncludeRepository := { x => false },
    publishTo <<= version { (v: String) =>
      val nexus = "https://defranexus.kainos.com/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("sonatype-snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("sonatype-releases"  at nexus + "content/repositories/releases")
    }
  )

  def defaultResolvers = Seq(
    "DEFRA Nexus Release repo" at "https://defranexus.kainos.com/content/repositories/releases/",
    "DEFRA Nexus third party repo" at "https://defranexus.kainos.com/content/repositories/thirdparty/"
  )

  def commonSettings = Seq(
    organization := "uk.gov.defra",
    autoScalaLibrary := false,
    scalaVersion := "2.10.2",
    crossPaths := false,
    resolvers ++= defaultResolvers,
    credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
  )


  def standardSettingsWithAssembly = commonSettings ++ Seq(
    assemblyMergeStrategy in assembly <<= (assemblyMergeStrategy in assembly) {
      (old) => {
        case PathList("javax", "servlet", xs@_*) => MergeStrategy.first
        case PathList("META-INF", "maven", "org.yaml", xs@_*) => MergeStrategy.first
        case "META-INF/spring.tooling" => MergeStrategy.discard
        case PathList("org", "apache", "commons", "logging", xs@_*) => MergeStrategy.first
        case PathList("com", "google", "common", xs@_*) => MergeStrategy.first
        case PathList("META-INF", "jersey-module-version", xs@_*) => MergeStrategy.first
        case "overview.html" | "about.html" | "unwanted.txt" | "manifest.mf" | "index.list" | "dependencies" | "notice.txt" | "notice" | "license" | "license.txt" => MergeStrategy.discard
        case x => old(x)
      }
    }
  )

  def standardSettingsWithPublished = commonSettings ++ appReleaseSettings

  lazy val sampleApi = Project("sample-service-api",
    file("sample-service-api"),
    settings = commonSettings ++ Seq(
      assemblyJarName in assembly := "sample-service-api.jar",
      name := "sample-service-api",
      libraryDependencies ++= apiDependencies
    )
  ).disablePlugins(AssemblyPlugin)

  lazy val sampleClient = Project("sample-service-client",
    file("sample-service-client"),
    settings = standardSettingsWithPublished ++ Seq(
      assemblyJarName in assembly := "sample-service-client.jar",
      name := "sample-service-client",
      libraryDependencies ++= clientDependencies ++ testDependencies
    )
  ).dependsOn(sampleApi).disablePlugins(AssemblyPlugin)

  lazy val sampleService = Project("sample-service",
    file("sample-service"),
    settings = standardSettingsWithAssembly ++ Seq(
      assemblyJarName in assembly := "sample-service.jar",
      name := "sample-service",
      libraryDependencies ++= serviceDependencies ++ testDependencies
    )
  ).dependsOn(sampleApi)

  lazy val root = Project("root", file("."),
    settings = Seq(
      publishLocal :=  Map.empty,
      publish :=  Map.empty,
      packagedArtifacts := Map.empty,
      mainClass in (Compile, run) := Some("uk.gov.defra.capd.sample.SampleServiceApplication")
    )
  ).aggregate(sampleApi, sampleClient, sampleService)
}



