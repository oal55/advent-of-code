/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details on building Java & JVM projects, please refer to https://docs.gradle.org/8.5/userguide/building_java_projects.html in the Gradle documentation.
 */

plugins {
    id("application")
    id("com.diffplug.spotless").version("6.23.3")
    id("pmd")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.guava)
    implementation("one.util:streamex:0.8.2")

    testImplementation(libs.junit.jupiter)
    testImplementation("org.assertj:assertj-core:3.11.1")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application { mainClass.set("aoc.Main") }

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

spotless {
    java {
        importOrder()
        removeUnusedImports()
        palantirJavaFormat()
    }
}

pmd {
    toolVersion = "6.55.0"
    sourceSets = listOf(pmd.sourceSets.find { it.name == SourceSet.MAIN_SOURCE_SET_NAME })
    ruleSetFiles = files("${rootDir}/.myJankConfigFiles/pmd-disable-some-rules.xml")
}

tasks.named<Test>("test") { useJUnitPlatform() }

// Janky task dependency shenanigans for debugging stuff o.O
//gradle.taskGraph.whenReady(closureOf<TaskExecutionGraph> {
//    println("Found task graph: $this")
//    println("Found " + allTasks.size + " tasks.")
//    allTasks.forEach { task ->
//        println(task)
//        task.dependsOn.forEach { dep ->
//            println("  - $dep")
//        }
//    }
//})
