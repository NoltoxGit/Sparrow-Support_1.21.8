plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.xenondevs.xyz/releases/")
    maven("https://libraries.minecraft.net/")
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://jitpack.io/")
}

dependencies {
    implementation(project(":common"))
    compileOnly("dev.folia:folia-api:1.20.4-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.2")
    compileOnly("org.incendo:cloud-paper:2.0.0-beta.6")
    compileOnly("org.incendo:cloud-minecraft-extras:2.0.0-beta.6")
    compileOnly("dev.dejvokep:boosted-yaml:1.3.4")
    compileOnly("xyz.xenondevs.invui:inventory-access:1.30")
    compileOnly("com.mojang:brigadier:1.0.18")
    compileOnly("com.github.Xiao-MoMi:Sparrow-Heart:0.3")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
}

tasks {
    shadowJar {
        archiveFileName.set("sparrow-bukkit.jarinjar")
        relocate("net.kyori", "net.momirealms.sparrow.libraries")
        relocate("org.incendo", "net.momirealms.sparrow.libraries")
        relocate("dev.dejvokep", "net.momirealms.sparrow.libraries")
        relocate("xyz.xenondevs", "net.momirealms.sparrow.libraries")
        relocate("de.tr7zw.changeme", "net.momirealms.sparrow.libraries")
        relocate("net.bytebuddy", "net.momirealms.sparrow.libraries.bytebuddy")
    }
}

artifacts {
    archives(tasks.shadowJar)
}
