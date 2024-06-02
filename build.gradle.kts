plugins {
    id("java")
    kotlin("jvm").version("2.0.0-Beta2")
    id("org.jetbrains.intellij.platform") version "2.0.0-beta5"
}

group = "dev.dexuby"
version = "1.0.0"

intellijPlatform {
    pluginConfiguration {
        id = "dev.dexuby.minecraftplugin"
        name = "Minecraft Plugin"
        description = """
            Plugin to create Minecraft plugin projects.
        """.trimIndent()
        vendor {
            name = "dexuby"
            url = "https://github.com/dexuby"
        }
    }
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2023.3.1")
        instrumentationTools()
        bundledPlugin("com.intellij.java")
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("233.*")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
