import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.5-SNAPSHOT" apply false
    java
    idea
}

val minecraftVersion = project.properties["minecraft_version"] as String

architectury.minecraft = minecraftVersion

subprojects {
    apply(plugin = "dev.architectury.loom")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://maven.parchmentmc.org")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.arcane-engineering.com/maven-releases")
        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://api.modrinth.com/maven")
                }
            }
            filter {
                includeGroup("maven.modrinth")
            }
        }
        maven {
            setUrl("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
            content {
                includeGroupByRegex("software\\.bernie.*")
                includeGroup("com.eliotlash.mclib")
            }
        }
        maven("https://maven.blamejared.com/")

    }

    @Suppress("UnstableApiUsage")
    dependencies {
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        "mappings"(loom.layered{
            officialMojangMappings()
            //parchment("org.parchmentmc.data:parchment-$minecraftVersion:${project.properties["parchment"]}@zip")
        })

        compileOnly("org.jetbrains:annotations:24.1.0")
    }
    loom.silentMojangMappingsLicense()
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "idea")

    version = project.properties["mod_version"] as String
    group = project.properties["maven_group"] as String
    base.archivesName.set(project.properties["archives_base_name"] as String)

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }

    java.withSourcesJar()
}
