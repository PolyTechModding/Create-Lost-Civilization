plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

val minecraftVersion = project.properties["minecraft_version"] as String

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentNeoForge").extendsFrom(configurations["common"])
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
}

dependencies {
    neoForge("net.neoforged:neoforge:${project.properties["neoforge_version"]}")
    modApi("dev.architectury:architectury-neoforge:${project.properties["architectury_version"]}")
    modApi("com.arcaneengineering:ArcaneLib-NeoForge-Arch:${project.properties["arcane_version"]}")
    modImplementation("software.bernie.geckolib:geckolib-neoforge-${project.properties["minecraft_version"]}:${project.properties["gecko_version"]}")
    modCompileOnlyApi("mezz.jei:jei-${project.properties["minecraft_version"]}-common-api:${project.properties["jei_version"]}")
    modCompileOnlyApi("mezz.jei:jei-${project.properties["minecraft_version"]}-neoforge-api:${project.properties["jei_version"]}")
    modRuntimeOnly("mezz.jei:jei-${project.properties["minecraft_version"]}-fabric:${project.properties["jei_version"]}")
    modLocalRuntime("maven.modrinth:wthit:${project.properties["neoforge_wthit_version"]}")
    modLocalRuntime("maven.modrinth:badpackets:neo-${project.properties["badpackets_version"]}")

    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionNeoForge")) { isTransitive = false }
}

tasks {
    base.archivesName.set(base.archivesName.get() + "-NeoForge")
    processResources {
        inputs.property("version", project.version)

        filesMatching("META-INF/mods.toml") {
            expand(mapOf("version" to project.version))
        }
    }

    shadowJar {
        exclude("fabric.mod.json")
        configurations = listOf(project.configurations.getByName("shadowCommon"))
        archiveClassifier.set("dev-shadow")
    }

    remapJar {
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
    publications.create<MavenPublication>("mavenNeoForge") {
        artifactId = "${project.properties["archives_base_name"]}" + "-NeoForge"
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