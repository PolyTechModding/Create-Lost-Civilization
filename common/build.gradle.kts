architectury {
    common("forge", "fabric", "neoforge")
    platformSetupLoomIde()
}

val minecraftVersion = project.properties["minecraft_version"] as String

loom.accessWidenerPath.set(file("src/main/resources/create_lost_civilization.accesswidener"))

sourceSets.main.get().resources.srcDir("src/main/generated/resources")

dependencies {
    // We depend on fabric loader here to use the fabric @Environment annotations and get the mixin dependencies
    // Do NOT use other classes from fabric loader
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")
    modApi("dev.architectury:architectury-fabric:${project.properties["architectury_version"]}")
    modApi("com.arcaneengineering:ArcaneLib:${project.properties["arcane_version"]}")
    modCompileOnly("software.bernie.geckolib:geckolib-fabric-${project.properties["minecraft_version"]}:${project.properties["gecko_version"]}")
    modCompileOnly("com.github.glitchfiend:TerraBlender-common:${project.properties["minecraft_version"]}-${project.properties["terrablender_version"]}")
    modCompileOnlyApi("mezz.jei:jei-${project.properties["minecraft_version"]}-common-api:${project.properties["jei_version"]}")
}

publishing {
    publications.create<MavenPublication>("mavenCommon") {
        artifactId = "${project.properties["archives_base_name"]}"
        from(components["java"])
    }

    repositories {
        mavenLocal()
        maven {
            val releasesRepoUrl = "https://example.com/releases"
            val snapshotsRepoUrl = "https://example.com/snapshots"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT") || project.version.toString().startsWith("0")) snapshotsRepoUrl else releasesRepoUrl)
            name = "ExampleRepo"
            credentials {
                username = project.properties["repoLogin"]?.toString()
                password = project.properties["repoPassword"]?.toString()
            }
        }
    }
}
