plugins {
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.6"
    // Keep application plugin so we can run the main class during development
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.security:spring-security-crypto")
    
    // BouncyCastle para Argon2 password hashing
    implementation("org.bouncycastle:bcprov-jdk18on:1.77")
    
    // Suporte para carregar .env automaticamente
    implementation("me.paulschwarz:spring-dotenv:4.0.0")

    // Databases
    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    
    // testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // implementation(libs.guava)
}

// Java toolchain
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the Spring Boot main class
    mainClass.set("com.voyago.Voyago")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
