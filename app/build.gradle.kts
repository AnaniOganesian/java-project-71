plugins {
    application
    id("com.github.ben-manes.versions") version "0.53.0"
    id("org.sonarqube") version "7.3.0.8198"
    checkstyle
    jacoco
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Core dependencies
    implementation("org.apache.commons:commons-lang3:3.20.0")
    implementation("info.picocli:picocli:4.7.7")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.15.2")

    annotationProcessor("info.picocli:picocli-codegen:4.7.7")

    // ===== JUnit 5 =====
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")

    // ===== JUnit Platform =====
    testImplementation("org.junit.platform:junit-platform-launcher:1.9.3")

    // AssertJ для удобных assertions
    testImplementation("org.assertj:assertj-core:3.24.1")

    // Mockito для мокирования
    testImplementation("org.mockito:mockito-core:5.7.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.7.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

// ===== КОМПИЛЯЦИЯ JAVA =====
tasks.compileJava {
    options.compilerArgs.addAll(listOf(
        "-Xlint:unchecked",
        "-Xlint:deprecation"
    ))
    options.compilerArgs.add("-Aproject=${project.group}/${project.name}")
}

tasks.compileTestJava {
    options.compilerArgs.addAll(listOf(
        "-Xlint:unchecked",
        "-Xlint:deprecation"
    ))
}

// ===== CHECKSTYLE КОНФИГУРАЦИЯ =====
checkstyle {
    toolVersion = "10.12.1"
    isShowViolations = true
    isIgnoreFailures = false
    configFile = file("config/checkstyle/checkstyle.xml")
}

tasks.checkstyleMain {
    source = fileTree("src/main/java") {
        exclude("**/CommandLine.java")
    }
    classpath = sourceSets["main"].compileClasspath

    reports {
        xml.required = true
        html.required = true
    }
}

tasks.checkstyleTest {
    source = fileTree("src/test/java")
    classpath = sourceSets["test"].compileClasspath

    reports {
        xml.required = true
        html.required = true
    }
}

// ===== ТЕСТЫ (ОДНА ЗАДАЧА!) =====
tasks.test {
    useJUnitPlatform()

    dependsOn("checkstyleMain", "checkstyleTest")

    testLogging {
        events("PASSED", "FAILED", "SKIPPED")
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        showStandardStreams = false
        showCauses = true
    }

    finalizedBy("jacocoTestReport")
}

// ===== JACOCO КОНФИГУРАЦИЯ =====
jacoco {
    toolVersion = "0.8.10"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required = true
        html.required = true
    }
}

// ===== SONARQUBE КОНФИГУРАЦИЯ =====
sonar {
    properties {
        property("sonar.projectKey", "AnaniOganesian_java-project-71")
        property("sonar.organization", "ananioganesian")
        property("sonar.sources", "src/main")
        property("sonar.tests", "src/test")
        property("sonar.coverage.jacoco.xmlReportPaths",
            "${layout.buildDirectory}/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.java.binaries",
            layout.buildDirectory.dir("classes/java/main").get().asFile.absolutePath)
    }
}

// ===== DEPENDENCY LOCKING =====
dependencyLocking {
    lockAllConfigurations()
}