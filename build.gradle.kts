@file:Suppress("UnstableApiUsage")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestStackTraceFilter

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("jvm") version "1.7.20"
    `java-library`
    `java-test-fixtures`
    signing
    `maven-publish`

    alias(libs.plugins.build.kover)
    alias(libs.plugins.build.ktlint)
    alias(libs.plugins.build.nexus)
}

repositories {
    mavenCentral()
}

group = "org.erwinkok.result"
version = "0.8.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation(libs.kotlin.logging)

    testImplementation(libs.kotlinx.coroutines.test)
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
            allWarningsAsErrors = true
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
            allWarningsAsErrors = true
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
            allWarningsAsErrors = true
            jvmTarget = "11"
            languageVersion = "1.7"
            apiVersion = "1.7"
        }
    }

    test {
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
}

kover {
    htmlReport {
        onCheck.set(true)
    }
}

koverMerged {
    enable()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set(project.name)
                inceptionYear.set("2022")
                url.set("https://github.com/erwin-kok/result-monad")
                licenses {
                    license {
                        name.set("BSD-3-Clause")
                        url.set("https://opensource.org/licenses/BSD-3-Clause")
                    }
                }
                developers {
                    developer {
                        name.set("Erwin Kok")
                        url.set("https://github.com/erwin-kok/")
                    }
                }
                scm {
                    url.set("https://github.com/erwin-kok/result-monad")
                    connection.set("scm:git:https://github.com/erwin-kok/result-monad")
                    developerConnection.set("scm:git:ssh://git@github.com:erwin-kok/result-monad.git")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/erwin-kok/result-monad/issues")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
        }
    }
}
