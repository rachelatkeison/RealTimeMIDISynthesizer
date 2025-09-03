plugins {
    java
    application
}

group = "com.rachel"
version = "1.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("com.rachel.midisynth.MIDISynth")
}

repositories {
    mavenCentral()
}

dependencies {
}

tasks.register<Jar>("runnableJar") {
    archiveBaseName.set("RealTimeMIDISynthesizer")
    archiveVersion.set("1.0")
    manifest {
        attributes["Main-Class"] = "com.rachel.midisynth.MIDISynth"
    }
    from(sourceSets.main.get().output)

    from("resources") {
        into("resources")
    }
}
