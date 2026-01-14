// Copyright (c) 2022 Erwin Kok. BSD-3-Clause license. See LICENSE file for more details.
@file:Suppress("UnstableApiUsage")

import com.adarshr.gradle.testlogger.theme.ThemeType
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    kotlin("jvm") version "2.3.0"
    `java-library`
    `java-test-fixtures`
    signing
    `maven-publish`

    alias(libs.plugins.build.kover)
    alias(libs.plugins.build.ktlint)
    alias(libs.plugins.build.nexus)
    alias(libs.plugins.build.versions)
    alias(libs.plugins.build.testlogger)
}

repositories {
    mavenCentral()
}

group = "org.erwinkok.result"
version = "1.5.0"

java {
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib"))

    implementation(libs.kotlin.logging)
    implementation(libs.slf4j.api)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)

    testFixturesImplementation(libs.junit.jupiter.api)
    testFixturesImplementation(libs.junit.jupiter.params)

    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

testlogger {
    theme = ThemeType.MOCHA
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            pom {
                name.set(project.name)
                description.set("Result monad for modelling success or failure results.")
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
                        id.set("erwin-kok")
                        name.set("Erwin Kok")
                        email.set("erwin.kok@protonmail.com")
                        url.set("https://github.com/erwin-kok/")
                        roles.set(listOf("owner", "developer"))
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
