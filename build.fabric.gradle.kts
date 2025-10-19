@file:Suppress("UnstableApiUsage")

plugins {
    id("fabric-loom")
    id("dev.kikugie.postprocess.jsonlang")
    id("me.modmuss50.mod-publish-plugin")
    id("maven-publish")
}

val minecraft = stonecutter.current.version
val mcVersion = stonecutter.current.project.substringBeforeLast('-')

tasks.named<ProcessResources>("processResources") {
    fun prop(name: String) = project.property(name) as String

    val props = HashMap<String, String>().apply {
        this["version"] = prop("mod.version") + "+" + prop("deps.minecraft")
        this["minecraft"] = prop("mod.mc_dep_fabric")
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }

}

tasks.named("processResources") {
    dependsOn(":${stonecutter.current.project}:stonecutterGenerate")
}

version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
base.archivesName = property("mod.id") as String

//loom {
//    accessWidenerPath = rootProject.file("src/main/resources/${property("mod.id")}.accesswidener")
//}

jsonlang {
    languageDirectories = listOf("assets/${property("mod.id")}/lang")
    prettyPrint = true
}

repositories {
    mavenLocal()
    maven ( "https://maven.minecraftforge.net" ) {
        name = "Minecraft Forge"
    }
    maven ( "https://maven.shedaniel.me/" ) {
        name = "shedaniel (Cloth Config)"
    }
    maven ( "https://maven.terraformersmc.com/releases/" ) {
        name = "Terraformers (Mod Menu)"
    }
    maven ( "https://maven.wispforest.io/releases/" ) {
        name = "Wisp Forest Maven"
    }
    maven ( "https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
    maven ( "https://maven2.bai.lol" ) {
        name = "WTHIT"
    }
    maven ( "https://repo.sleeping.town/" ) {
        name = "Sisby Maven"
    }
    maven ( "https://maven.parchmentmc.org" ) {
        name = "Parchment Mappings"
    }
    maven ( "https://maven.isxander.dev/releases") {
        name = "Xander Maven"
    }
    maven ( "https://maven.nucleoid.xyz" ) {
        name = "Nucleoid Maven (Polymer)"
    }
    maven ( "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs Mod Resources"
    }

    maven("https://mvn.devos.one/releases/" )
    maven("https://mvn.devos.one/snapshots/" )
    maven("https://maven.jamieswhiteshirt.com/libs-release")
    maven("https://maven.ladysnake.org/releases")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("deps.minecraft")}")
    mappings(loom.layered {
        officialMojangMappings()
        if (hasProperty("deps.parchment"))
            parchment("org.parchmentmc.data:parchment-${property("deps.parchment")}@zip")
    })


    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    include("folk.sisby:kaleido-config:${property("deps.kaleido")}")

    // Fast Item Frames
    if (hasProperty("deps.fast_item_frames")) {
        modCompileOnly("maven.modrinth:fast-item-frames:${mod.dep("fast_item_frames")}")
        modRuntimeOnly("maven.modrinth:fast-item-frames:${mod.dep("fast_item_frames")}")
        modRuntimeOnly("maven.modrinth:puzzles-lib:${mod.dep("puzzles_lib")}")
        modRuntimeOnly("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:${mod.dep("forge_config_api_port")}")
    } else {
        modCompileOnly("maven.modrinth:fast-item-frames:GVMdKuJR")
    }

    modImplementation("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("deps.fabric_api")}")

    // Cloth Config
    if (hasProperty("deps.cloth_version")) {
        modApi("me.shedaniel.cloth:cloth-config-fabric:${property("deps.cloth_version")}")
    } else {
        modCompileOnly("me.shedaniel.cloth:cloth-config-fabric:19.0.147")
    }
    // Mod Menu
    if (hasProperty("deps.modmenu_version"))
        modApi("com.terraformersmc:modmenu:${property("deps.modmenu_version")}")
    else {
        modCompileOnly("com.terraformersmc:modmenu:15.0.0-beta.3")
    }

    // Jade
    if (hasProperty("deps.jade")) {
        modCompileOnly("maven.modrinth:jade:${property("deps.jade")}")
        modLocalRuntime("maven.modrinth:jade:${property("deps.jade")}")
    } else {
        modCompileOnly("maven.modrinth:jade:19.3.2+fabric")
    }

}

configurations.all {
    resolutionStrategy {
        force("net.fabricmc:fabric-loader:${property("deps.fabric_loader")}")
    }
}

tasks {
    processResources {
        exclude("**/neoforge.mods.toml", "**/mods.toml")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(remapJar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.21")) {
        JavaVersion.VERSION_21
    } else {
        JavaVersion.VERSION_17
    }
    sourceCompatibility = javaCompat
    targetCompatibility = javaCompat
}

val additionalVersionsStr = findProperty("publish.additionalVersions") as String?
val additionalVersions: List<String> = additionalVersionsStr
    ?.split(",")
    ?.map { it.trim() }
    ?.filter { it.isNotEmpty() }
    ?: emptyList()

publishMods {
    file = tasks.remapJar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.remapSourcesJar.map { it.archiveFile.get() })

    // one of BETA, ALPHA, STABLE
    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} Fabric"
    version = "${property("mod.version")}+${property("deps.minecraft")}-fabric"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("fabric")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
        optional("mcqoy")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        requires("fabric-api")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.clickthrough"
            artifactId = "clickthrough-plus-fabric"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}