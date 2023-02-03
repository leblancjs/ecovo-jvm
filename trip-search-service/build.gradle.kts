plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("io.freefair.lombok") version "6.6.1"
}

group = "com.solutionsjsleblanc.ecovo"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("au.com.dius.pact.consumer:junit5:4.4.4")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
