import cz.habarta.typescript.generator.EnumMapping
import cz.habarta.typescript.generator.JsonLibrary
import cz.habarta.typescript.generator.TypeScriptFileType
import cz.habarta.typescript.generator.TypeScriptOutputKind

plugins {
  java
  alias(libs.plugins.springBoot)
  alias(libs.plugins.springDependencyManagement)
  alias(libs.plugins.typescriptGenerator)
}

group = "eu.possiblex"
version = "0.0.1"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(17)
  }
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(libs.springBootStarterActuator)
  implementation(libs.springBootStarterWeb)
  implementation(libs.springBootStarterWebflux)
  implementation(libs.springBootStarterDataJpa)
  implementation(libs.springBootStarterSecurity)
  implementation(libs.hibernateValidator)
  implementation(libs.openApi)
  implementation(libs.titaniumJsonLd)
  implementation(libs.jakartaJson)
  implementation(libs.postgresql)
  compileOnly(libs.lombok)
  annotationProcessor(libs.lombokMapStructBinding)
  implementation(libs.mapStruct)
  annotationProcessor(libs.mapStructProcessor)
  developmentOnly(libs.springBootDevtools)
  runtimeOnly(libs.therApi)
  annotationProcessor(libs.lombok)
  annotationProcessor(libs.therApiScribe)
  testImplementation(libs.springBootStarterTest)
  testImplementation(libs.reactorTest)
  testImplementation(libs.h2)
  testImplementation(libs.springSecurityTest)
  testRuntimeOnly(libs.jUnit)
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}


tasks.bootJar {
  mainClass.set("eu.possiblex.portal.PortalApplication")
  archiveBaseName.set("backend")
}

tasks.getByName<Jar>("jar") {
  enabled = false
}

tasks.register("buildBackend") {
  dependsOn(":backend:build")
  description = "Builds the backend application."
  group = "build"
}


tasks.register<JavaExec>("startBackend") {
  dependsOn("bootJar")
  description = "Runs the backend application."
  group = "application"
  mainClass.set("eu.possiblex.portal.PortalApplication")
  classpath = sourceSets["main"].runtimeClasspath
  val activeProfile = project.findProperty("activeProfile")?.toString()
  if (activeProfile != null) {
    systemProperty("spring.profiles.active", activeProfile)
  }

}

tasks {
  generateTypeScript {
    jsonLibrary = JsonLibrary.jackson2
    outputKind = TypeScriptOutputKind.module
    outputFileType = TypeScriptFileType.implementationFile
    scanSpringApplication = true
    generateSpringApplicationClient = true
    addTypeNamePrefix = "I"
    classPatterns = listOf(
      "eu.possiblex.portal.application.entity.**",
      "eu.possiblex.portal.application.boundary.**"
    )
    outputFile = "../frontend/src/app/services/mgmt/api/backend.ts"
    noFileComment = true
    mapEnum = EnumMapping.asEnum
  }
}