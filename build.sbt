scalaVersion := "2.13.5"
organization := "world.ultravanilla"
version := "2.0-SNAPSHOT"
name := "UltraVanilla-2"
resolvers ++= List(
    "jitpack.io" at "https://jitpack.io",
    "papermc" at "https://papermc.io/repo/repository/maven-public/",
    "dmulloy2-repo" at "https://repo.dmulloy2.net/nexus/repository/public/",
    "jcenter" at "https://jcenter.bintray.com",
    "m2-dv8tion" at "https://m2.dv8tion.net/releases"
)
libraryDependencies ++= List(
    "io.papermc.paper" % "paper-api" % "1.18-rc3-R0.1-SNAPSHOT" % Provided,
    "net.dv8tion" % "JDA" % "4.3.0_277" exclude("club.minnced", "opus-java"),
    "org.tomlj" % "tomlj" % "1.0.0"
)

scalacOptions += "-target:17"
javacOptions ++= Seq("-source", "17", "-target", "17")
cancelable in Global := true

Compile / resourceGenerators += Def.task {
    val file = (Compile / resourceManaged).value / "plugin.yml"
    val pluginConfig = scala.io.Source.fromFile("plugin.yml").getLines.mkString("\n")
    val contents = pluginConfig.replace("@VERSION@", version.value)
    IO.write(file, contents)
    Seq(file)
}.taskValue

assembly / assemblyMergeStrategy := {
    case "module-info.class" => MergeStrategy.first
    case x =>
        val oldStrategy = (assembly / assemblyMergeStrategy).value
        oldStrategy(x)
}

val sbtDefaultTarget = System.getProperty("sbtDefaultTarget", "false")
val sbtOutputDirectory = System.getProperty("sbtOutputDirectory", "Server/plugins")

assembly / assemblyOutputPath := {
    val default = (assembly / assemblyOutputPath).value
    if (sbtDefaultTarget == "true") {
        default
    } else {
        file(sbtOutputDirectory + "/" + name.value + "-" + version.value + ".jar")
    }
}
