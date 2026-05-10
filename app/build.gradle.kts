
plugins {
    id("java")
    application
    id("com.github.ben-manes.versions") version "0.53.0"
    // id("org.sonarqube") version "7.1.0.6387"
    checkstyle

}



application {
    mainClass = "hexlet.code.App"

}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.20.0")
    implementation("info.picocli:picocli:4.7.7")
    annotationProcessor("info.picocli:picocli-codegen:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")

}

tasks.compileJava {
    options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}


tasks.test {
    useJUnitPlatform()
}