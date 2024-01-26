import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	jacoco
	id("org.springframework.boot") version "3.2.1"
	id("io.spring.dependency-management") version "1.1.4"
	id("org.sonarqube") version "4.4.1.3373"
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

	implementation("org.springframework.cloud:spring-cloud-aws-messaging:2.1.3.RELEASE")
	implementation("software.amazon.awssdk:sqs:2.16.24")
	implementation("com.amazonaws:aws-java-sdk-core:1.11.589")
	implementation("com.amazonaws:aws-java-sdk:1.11.584")

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

jacoco {
	toolVersion = "0.8.9"
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required = true
		csv.required = false
		html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
	}
}

tasks.withType<JacocoCoverageVerification> {
	afterEvaluate {
		classDirectories.setFrom(files(classDirectories.files.map {
			fileTree(it).apply {
				exclude(
					"**/config/**",
					"**/exception/**",
					"**/ControllerAdvice.*",
					"**/FastFoodApplication.*"
				)
			}
		}))
	}
}

tasks.withType<JacocoReport> {
	afterEvaluate {
		classDirectories.setFrom(files(classDirectories.files.map {
			fileTree(it).apply {
				exclude(
					"**/config/**",
					"**/exception/**",
					"**/ControllerAdvice.*",
					"**/FastFoodPagamentoApplication.*"
				)
			}
		}))
	}
}

apply(plugin = "org.sonarqube")

val coverageExclusions = listOf(
	"**/config/**",
	"**/exception/**",
	"**/ControllerAdvice.*",
	"**/FastFoodApplication.*"
)

sonar {
	properties {
		property("sonar.projectKey", "fiappostech_sonar-fiap-pos-tech")
		property("sonar.organization", "fiappostech")
		property("sonar.projectName", "fast-food-pagamento")
		property("sonar.host.url", "https://sonarcloud.io")
		property("sonar.coverage.jacoco.xmlReportPaths", layout.buildDirectory.dir("/reports/jacoco/test/*.xml"))
		property("sonar.java.coveragePlugin", "jacoco")
		property("sonar.coverage.exclusions",coverageExclusions)
		property("sonar.exclusions", coverageExclusions)
	}
}
