# POSSIBLE-X Portal

The POSSIBLE-X Portal is the centralized landing page for interacting with the POSSIBLE-X Dataspace.

It consists of three different parts:

* The main page which is the landing page and links to other pages.
* The registration page for registering new participants.
* An administrative page to manage registration requests.

For registration, it allows new users to send a registration request and upon administrative approval, receive a Gaia-X
compliant participant credential and dataspace identity, which can be used for further functionalities in the dataspace.

## Repository structure

The repository is structured as a Gradle multi-project build.

```
(...)
├── libs.versions.toml          # configuration file of version catalog for dependencies
├── settings.gradle.kts         # root project settings
├── build.gradle.kts            # root build file
├── buildSrc/                   # shared build configuration
├── frontend/                   # Angular frontend code for the GUI
│   └── build.gradle.kts        # build file for the Angular frontend
├── backend/                    # Spring backend
│   └── build.gradle.kts        # build file for the Spring backend
```

## (Re-)Generate Typescript API interfaces and REST client

The typescript API interfaces and the corresponding REST client are auto-generated from the Spring backend entity and
controller classes using the following command:

```
./gradlew generateTypeScript
```

Afterward they can be found at

```
frontend/src/app/services/mgnt/api/backend.ts
```

## Build Backend

If you only want to build the backend, you can run

```
./gradlew buildBackend
```

after which the built jar can be found at `backend/build/libs/backend-x.y.z.jar`

## Run Backend

Through gradle:

```
./gradlew startBackend
```

Running a specific configuration:

E.g. for local portal:

```
./gradlew startBackend -PactiveProfile=local
```

Alternatively running the jar directly (if built previously):

```
java -jar backend/build/libs/backend-x.y.z.jar --spring.profiles.active=-local
```

Once the service is running, you can access it at http://localhost:8088/.

The OpenAPI documentation can be found at http://localhost:8088/swagger-ui.html .

## Build Frontend

If you only want to build the frontend, you can run

```
./gradlew buildFrontend
```

after which the built frontend can be found at `frontend/build/resources/`.

## Run Frontend

Through gradle:

```
./gradlew startFrontend
```

Running a specific configuration:

E.g. for local portal:

```
./gradlew startFrontend -PactiveProfile=local
```

Alternatively running with npm directly:

```
npm --prefix frontend/ run ng -- serve --configuration local --port 4208
```

Once the service is running, you can access it at http://localhost:4208/.

## Run Full Application (Frontend and Backend)

In addition to running the frontend and backend individually, there is also a gradle task for running both in parallel.
Note that when the app is started through this task, the IntelliJ debugger will not be able to attach to the backend and
hence won't stop at breakpoints.
Through gradle:

```
./gradlew startFull
```

Running a specific configuration:

E.g. for local portal:

```
./gradlew startFull -PactiveProfile=local
```

## Killing orphaned processes

If for any reason the application is not shut down properly, the following command can be used to kill a service that is
running on the specified port:

```
sudo fuser -k 8088/tcp
```

where 8088 can be replaced with any other port.