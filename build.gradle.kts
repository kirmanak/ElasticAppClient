plugins {
    kotlin("jvm") version "1.3.61"
}

group = "ru.ifmo.kirmanak"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.kubernetes:client-java:5.0.0")
    // implementation("org.apache.xmlrpc:xmlrpc:3.1.3")
    implementation("org.apache.xmlrpc:xmlrpc-client:3.1.3")
    implementation(kotlin("stdlib-jdk8"))
    implementation(files("libs/org.opennebula.client.jar"))
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
}