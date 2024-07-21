plugins {
    application

    id("com.autonomousapps.dependency-analysis") version "1.32.0"
    id("com.diffplug.spotless") version "6.25.0"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("se.ascp.gradle.gradle-versions-filter") version "0.1.16"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("com.fasterxml.jackson:jackson-bom:2.17.2"))

    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-smile")
    implementation("commons-io:commons-io:2.16.1")
    implementation("info.picocli:picocli:4.7.6")
}

application {
    mainClass = "Main"
}

versionsFilter {
    gradleReleaseChannel.set("current")
    checkConstraints.set(true)
    outPutFormatter.set("json")
}

spotless {
    java {
        googleJavaFormat("1.22.0").reflowLongStrings().skipJavadocFormatting()
        formatAnnotations()
        targetExclude("build/**")
    }
}
