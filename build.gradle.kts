import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.21"
	kotlin("plugin.spring") version "1.9.21"
}

group = "br.com.fiap.postech.pagamento"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.json:json:20231013")
	implementation("org.springframework.cloud:spring-cloud-starter-aws-messaging:2.2.1.RELEASE")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")

	testImplementation(group = "io.mockk", name = "mockk", version = "1.10.2")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:2.2.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
