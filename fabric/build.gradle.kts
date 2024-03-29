plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val minecraftVersion = project.properties["minecraft_version"] as String

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentFabric").extendsFrom(configurations["common"])
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    runs.create("datagen") {
        client()
        name("Data Generation")
        vmArg("-Dfabric-api.datagen")
        vmArg("-Dfabric-api.datagen.output-dir=${project(":common").file("src/main/generated/resources").absolutePath}")
        vmArg("-Dfabric-api.datagen.modid=create_lost_civilization")

        runDir("build/datagen")
    }
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${project.properties["fabric_loader_version"]}")
    modApi("net.fabricmc.fabric-api:fabric-api:${project.properties["fabric_api_version"]}+$minecraftVersion")
    modApi("dev.architectury:architectury-fabric:${project.properties["architectury_version"]}")
    modImplementation("software.bernie.geckolib:geckolib-fabric-${project.properties["minecraft_version"]}:${project.properties["gecko_version"]}")
    modImplementation("com.github.glitchfiend:TerraBlender-fabric:${project.properties["minecraft_version"]}-${project.properties["terrablender_version"]}")
    modApi("com.arcaneengineering:ArcaneLib-Fabric-Arch:${project.properties["arcane_version"]}")
    modCompileOnlyApi("mezz.jei:jei-${project.properties["minecraft_version"]}-common-api:${project.properties["jei_version"]}")
    modCompileOnlyApi("mezz.jei:jei-${project.properties["minecraft_version"]}-fabric-api:${project.properties["jei_version"]}")

    modLocalRuntime("maven.modrinth:sodium:mc${project.properties["minecraft_version"]}-${project.properties["sodium_version"]}")
    modLocalRuntime("maven.modrinth:iris:${project.properties["iris_version"]}+${project.properties["minecraft_version"]}")
    modLocalRuntime("maven.modrinth:modmenu:${project.properties["modmenu_version"]}")
    modLocalRuntime("maven.modrinth:reeses-sodium-options:mc${project.properties["minecraft_version"]}-${project.properties["sodium_options_version"]}")
    modLocalRuntime("maven.modrinth:wthit:${project.properties["fabric_wthit_version"]}")
    modLocalRuntime("maven.modrinth:badpackets:fabric-${project.properties["badpackets_version"]}")

    modRuntimeOnly("mezz.jei:jei-${project.properties["minecraft_version"]}-fabric:${project.properties["jei_version"]}")

    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionFabric")) { isTransitive = false }
}

tasks {
    base.archivesName.set(base.archivesName.get() + "-Fabric")
    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
    }

    jar.get().archiveClassifier.set("dev")

    sourcesJar {
        val commonSources = project(":common").tasks.sourcesJar
        dependsOn(commonSources)
        from(commonSources.get().archiveFile.map { zipTree(it) })
    }
}

components {
    java.run {
        if (this is AdhocComponentWithVariants)
            withVariantsFromConfiguration(project.configurations.shadowRuntimeElements.get()) { skip() }
    }
}

publishing {
    publications.create<MavenPublication>("mavenFabric") {
        artifactId = "${project.properties["archives_base_name"]}" + "-Fabric"
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
