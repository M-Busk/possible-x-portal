# POSSIBLE-X Portal Backend

The backend component of the POSSIBLE-X Portal is a Spring Boot application that provides the REST API for the frontend.
It interacts with the database to store and retrieve user registration requests for participant credentials.
It is also responsible for administrating requests as well as sending them to the central POSSIBLE-X Catalogue /
Fraunhofer (FH) Catalog upon approval. Once a user's registration request is approved, the backend will also request a
DAPS certificate / dataspace identity from the central DAPS server for the new dataspace participant and create a DID
identity on the POSSIBLE-X DID Web Service.

## Structure

```
├── src/main/java/eu/possiblex/portal
│   ├── application         # application layer
│   │   ├── boundary        # external REST API controllers
│   │   ├── configuration   # configuration-related components
│   │   ├── control         # internal services for processing data such as mappers
│   │   └── entity          # application data models
│   ├── business            # business logic layer
│   │   ├── control         # business logic services
│   │   └── entity          # business logic data models
│   ├── persistence         # persistence layer
│   │   ├── control         # internal services for processing data such as mappers
│   │   ├── dao             # data access objects for database operations
│   │   └── entity          # persistence data models
│   ├── utilities           # shared static functionality
```

## Configuration

For a full list of configuration options (including Spring/JPA options) please see the
[application.yml](src/main/resources/application.yml).

| Key                             | Description                                                                                               | Default                                           |
|---------------------------------|-----------------------------------------------------------------------------------------------------------|---------------------------------------------------|
| server.port                     | Sets the https port under which the service will run                                                      | 8080                                              |
| sd-creation-wizard-api.base-url | Sets the SD Creation Wizard API Service url                                                               | http://localhost:8085                             |
| spring.datasource.url           | Sets the path/url to the database                                                                         | "jdbc:postgresql://localhost:5432/possibleportal" |
| spring.datasource.username      | Sets the database username                                                                                | "postgres"                                        |
| spring.datasource.password      | Sets the database password                                                                                | "postgres"                                        |
| spring.security.admin.username  | Sets the admin username for the portal                                                                    | admin                                             |
| spring.security.admin.password  | Sets the admin password for the portal                                                                    | admin                                             |
| daps-server.url.internal        | Sets the internal DAPS server url                                                                         | http://localhost:4567                             |
| daps-server.url.external        | Sets the external DAPS server url that is reachable from the public internet                              | http://localhost:4567                             |
| did-web-service.base-url        | Sets the DID Web Service url                                                                              | https://localhost:4443                            |
| did-web-service.ignore-ssl      | Sets the flag whether SSL should be ignored or not for the DID Web Service (useful for local development) | true                                              |
| fh.catalog.ui-url               | Sets the FH Catalog UI url (refers to the UI where, e.g. Service Offerings can be browsed)                | https://possible.fokus.fraunhofer.de/             |
| fh.catalog.url                  | Sets the FH Catalog url of the "piveau-hub-repo" component                                                | url                                               |
| fh.catalog.secret-key           | Sets the secret key for API interactions with the FH Catalog                                              | secret                                            |
| version.no                      | Sets the version number of the portal which is shown in the POSSIBLE-X Portal UI                          | "1.0.0"                                           |
| version.date                    | Sets the version date of the portal which is shown in the POSSIBLE-X Portal UI                            | "2024-12-31"                                      |

## Build

If you only want to build the project, you can go to the root of the repository and run

```
./gradlew buildBackend
```

after which the built jar can be found at `backend/build/libs/backend-x.y.z.jar`.

## Run

The configuration options mentioned above can be set via environment variables as needed.
Below are some examples, please replace the right hand side with the actual values.

```
export SDCREATIONWIZARDAPI_BASEURL="SD Creation Wizard API base URL"
export DAPSSERVER_URL_INTERNAL="DAPS Server internal URL"
export DAPSSERVER_URL_EXTERNAL="DAPS Server external URL"
export SPRING_DATASOURCE_URL="Datasource URL"
export SPRING_DATASOURCE_USERNAME="Datasource Username"
export SPRING_DATASOURCE_PASSWORD="Datasource Password"
```

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
java -jar backend/build/libs/backend-x.y.z.jar
```

Or running the jar with a specific configuration, e.g. for local portal:

```
java -jar backend/build/libs/backend-x.y.z.jar --spring.profiles.active=-local
```

Once the service is running, you can access it at http://localhost:8088/ .

The OpenAPI documentation can be found at http://localhost:8088/swagger-ui.html .