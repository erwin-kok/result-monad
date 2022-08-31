import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestStackTraceFilter

// Without these suppressions, version catalog usage here and in other build
// files is marked red by IntelliJ:
// https://youtrack.jetbrains.com/issue/KTIJ-19369.
@Suppress(
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    kotlin("jvm") version "1.7.10"
    `java-library`

    id("java-test-fixtures")
    id("maven-publish")
    id("signing")

    alias(libs.plugins.build.kover)
    alias(libs.plugins.build.ktlint)
}

repositories {
    mavenCentral()
}

group = "org.erwinkok.result"
version = "0.1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
    withSourcesJar()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(libs.kotlin.logging)

    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)

    testFixturesImplementation(libs.junit.jupiter.api)
    testFixturesImplementation(libs.junit.jupiter.params)

    testRuntimeOnly(libs.junit.jupiter.engine)
}

tasks {
    compileKotlin {
        println("Configuring KotlinCompile $name in project ${project.name}...")
        kotlinOptions {
            @Suppress("SpellCheckingInspection")
            freeCompilerArgs = listOf("-Xjsr305=strict")
            allWarningsAsErrors = false
            jvmTarget = "11"
            languageVersion = "1.7"
            apiVersion = "1.7"
        }
    }

    compileTestKotlin {
        println("Configuring KotlinTestCompile $name in project ${project.name}...")
        kotlinOptions {
            @Suppress("SpellCheckingInspection")
            freeCompilerArgs = listOf("-Xjsr305=strict")
            allWarningsAsErrors = false
            jvmTarget = "11"
            languageVersion = "1.7"
            apiVersion = "1.7"
        }
    }

    compileTestFixturesKotlin {
        println("Configuring KotlinTestFixturesCompile $name in project ${project.name}...")
        kotlinOptions {
            @Suppress("SpellCheckingInspection")
            freeCompilerArgs = listOf("-Xjsr305=strict")
            allWarningsAsErrors = false
            jvmTarget = "11"
            languageVersion = "1.7"
            apiVersion = "1.7"
        }
    }
}

kover {
    htmlReport {
        onCheck.set(true)
    }
}

koverMerged {
    enable()
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(TestLogEvent.PASSED, TestLogEvent.FAILED)
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        maxGranularity = 3
        stackTraceFilters = setOf(TestStackTraceFilter.ENTRY_POINT)
    }
}

publishing {
    repositories {
        maven {
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("$buildDir/repo")
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
}
