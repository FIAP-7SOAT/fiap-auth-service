import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	val kotlinVersion = "1.9.24"

	kotlin("jvm") version kotlinVersion
	kotlin("plugin.spring") version kotlinVersion
	kotlin("plugin.jpa") version kotlinVersion
	kotlin("plugin.noarg") version kotlinVersion

	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.flywaydb.flyway") version "10.13.0"
	id("jacoco")
}

group = "br.com.fiap"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://jitpack.io")
	}
}

val kotlinLoggingVersion = "3.0.5"
val mockkVersion = "1.13.11"

dependencies {
	modules {
		module("org.springframework.boot:spring-boot-starter-tomcat") {
			replacedBy("org.springframework.boot:spring-boot-starter-undertow", "Use Undertow instead of Tomcat")
		}
	}

	// Jackson e Kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	// Logging
	implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")

	// Spring Boot e Security
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-web")
	implementation("org.springframework.security:spring-security-config")
	implementation("org.springframework.security:spring-security-crypto")

	// Data e JPA
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql:42.3.3")

	// Actuator e OpenAPI
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// Email
	implementation("org.springframework.boot:spring-boot-starter-mail")
	implementation("com.sun.mail:jakarta.mail:2.0.1")

	// JJWT
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Jakarta e Validator
	implementation("org.hibernate.validator:hibernate-validator:8.0.1.Final")
	implementation("org.glassfish:jakarta.el:4.0.2")

	// Testes
	testImplementation("com.h2database:h2")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("io.mockk:mockk:$mockkVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
//		freeCompilerArgs += listOf("-Xexplicit-api=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

jacoco {
	toolVersion = "0.8.8"
}

tasks.jacocoTestReport {
	dependsOn("test")

	reports {
		xml.required.set(true)
		html.required.set(true)
	}

	classDirectories.setFrom(
		fileTree(layout.buildDirectory.dir("classes/kotlin/main")) {
			exclude(
//                "**/auth/controller/**",     // Excluindo controllers de autenticação
//                "**/auth/service/**",        // Excluindo serviços de autenticação
//                "**/auth/model/**",          // Excluindo modelos de autenticação
//                "**/user/controller/**",     // Excluindo controllers de usuários
//                "**/user/service/**",        // Excluindo serviços de usuários
//                "**/user/repository/**",     // Excluindo repositórios de usuários
//                "**/user/model/**",          // Excluindo modelos de usuários
				"**/notification/service/**", // Excluindo serviços de notificação
				"**/configuration/**",       // Excluindo configurações
				"**/util/**",                // Excluindo utilitários
				"**/generated/**"            // Código gerado
			)
		}
	)
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)

	violationRules {
		rule {
			limit {
				minimum = 0.50.toBigDecimal()
			}
		}
	}
}
