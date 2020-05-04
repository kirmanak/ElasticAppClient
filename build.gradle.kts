import org.gradle.kotlin.dsl.support.unzipTo

plugins {
    kotlin("jvm") version "1.3.72"
    `java-library`
    `maven-publish`
    signing
}

group = "ru.ifmo.kirmanak"
version = "1.0.0"

repositories {
    mavenCentral()
}

val openNebulaLib = "libs/org.opennebula.client.jar"

dependencies {
    api("io.kubernetes:client-java:8.0.0")
    api(files(openNebulaLib))

    implementation("org.apache.xmlrpc:xmlrpc-client:3.1.3")
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("junit:junit:4.13")
    testImplementation("org.slf4j:slf4j-simple:1.7.30")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    jar {
        val unzippedPath = "$buildDir/unzipped/"
        unzipTo(file(unzippedPath), file(openNebulaLib))
        delete("$unzippedPath/META-INF")
        from(unzippedPath)
    }

    javadoc {
        if (JavaVersion.current().isJava9Compatible) {
            (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
        }
    }
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "elastic-app-client"
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Elastic App Client")
                description.set("Library providing access to various virtualized infrastructure providers")
                url.set("https://github.com/kirmanak/ElasticAppClient")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("kirmanak")
                        name.set("Kirill Kamakin")
                        email.set("k.kamakin@protonmail.ch")
                    }
                }
                scm {
                    connection.set("scm:git:git://git@github.com:kirmanak/ElasticAppClient.git")
                    developerConnection.set("scm:git:ssh://git@github.com:kirmanak/ElasticAppClient.git")
                    url.set("https://github.com/kirmanak/ElasticAppClient")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri("https://github.com/kirmanak/ElasticAppClient/releases")
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
