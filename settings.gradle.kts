rootProject.name = "possible-x-portal"

dependencyResolutionManagement {
  versionCatalogs {
    create("libs") {
      from(files("libs.versions.toml"))
    }
  }
}

include("backend", "frontend")