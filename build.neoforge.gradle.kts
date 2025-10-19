plugins {
    id("net.neoforged.moddev")
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
        this["minecraft"] = prop("mod.mc_dep_forgelike")
    }

    filesMatching(listOf("fabric.mod.json", "META-INF/neoforge.mods.toml", "META-INF/mods.toml")) {
        expand(props)
    }
}

version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
base.archivesName = property("mod.id") as String

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
    maven ( "https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") {
        name = "Fuzs Mod Resources"
    }

    maven ( "https://cursemaven.com" )
}

neoForge {
    version = property("deps.neoforge") as String
    validateAccessTransformers = true

    if (hasProperty("deps.parchment")) parchment {
        val (mc, ver) = (property("deps.parchment") as String).split(':')
        mappingsVersion = ver
        minecraftVersion = mc
    }

    runs {
        register("client") {
            gameDirectory = file("run/")
            client()
        }
        register("server") {
            gameDirectory = file("run/")
            server()
        }
    }

    mods {
        register(property("mod.id") as String) {
            sourceSet(sourceSets["main"])
        }
    }
}

tasks {
    processResources {
        exclude("**/fabric.mod.json", "**/*.accesswidener", "**/mods.toml")
    }

    named("createMinecraftArtifacts") {
        dependsOn("stonecutterGenerate")
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        from(jar.map { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod.version")}"))
        dependsOn("build")
    }
}

dependencies {

    implementation("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    jarJar("folk.sisby:kaleido-config:${property("deps.kaleido")}")
    "additionalRuntimeClasspath"("folk.sisby:kaleido-config:${property("deps.kaleido")}")


    // Cloth Config
    if (hasProperty("deps.cloth_version")) {
        implementation("me.shedaniel.cloth:cloth-config-neoforge:${property("deps.cloth_version")}")
    } else {
        compileOnly("me.shedaniel.cloth:cloth-config-neoforge:19.0.147")
    }
    // Jade
    if (hasProperty("deps.jade")) {
        runtimeOnly("maven.modrinth:jade:${property("deps.jade")}")
    }
    compileOnly("maven.modrinth:jade:19.3.1+neoforge")

    // Fast Item Frames
    if (stonecutter.eval(mcVersion, ">1.20.1")) {
        implementation("maven.modrinth:fast-item-frames:${mod.dep("fast_item_frames")}")
        implementation("maven.modrinth:puzzles-lib:${mod.dep("puzzles_lib")}")
        runtimeOnly("fuzs.forgeconfigapiport:forgeconfigapiport-neoforge:${mod.dep("forge_config_api_port")}")
    }


}

java {
    withSourcesJar()
    val javaCompat = if (stonecutter.eval(stonecutter.current.version, ">=1.20.5")) {
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
    file = tasks.jar.map { it.archiveFile.get() }
    additionalFiles.from(tasks.named<org.gradle.jvm.tasks.Jar>("sourcesJar").map { it.archiveFile.get() })

    type = STABLE
    displayName = "${property("mod.name")} ${property("mod.version")} for ${stonecutter.current.version} NeoForge"
    version = "${property("mod.version")}+${property("deps.minecraft")}-neoforge"
    changelog = provider { rootProject.file("CHANGELOG-LATEST.md").readText() }
    modLoaders.add("neoforge")

    modrinth {
        projectId = property("publish.modrinth") as String
        accessToken = env.MODRINTH_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
        optional("mcqoy")
    }

    curseforge {
        projectId = property("publish.curseforge") as String
        accessToken = env.CURSEFORGE_API_KEY.orNull()
        minecraftVersions.add(stonecutter.current.version)
        minecraftVersions.addAll(additionalVersions)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "cc.cassian.clickthrough"
            artifactId = "clickthrough-plus-neoforge"
            version = "${property("mod.version")}+${property("deps.minecraft")}"

            from(components["java"])
        }
    }
}