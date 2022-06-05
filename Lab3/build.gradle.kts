import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

group = "ru.stolexiy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:3.141.59")
//    testImplementation("org.seleniumhq.selenium:selenium-api:3.141.59")
    testImplementation("ru.yandex.qatools.htmlelements:htmlelements-java:1.20.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
    // https://mvnrepository.com/artifact/org.mockito/mockito-core
    testImplementation("org.mockito:mockito-core:4.4.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    // https://mvnrepository.com/artifact/org.mockito/mockito-junit-jupiter
    testImplementation("org.mockito:mockito-junit-jupiter:4.4.0")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("$group.MainKt")
}