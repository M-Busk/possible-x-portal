# POSSIBLE-X Portal Backend

The backend component of the POSSIBLE-X Portal is a Spring Boot application that provides the REST API for the frontend.
It interacts with the database to store and retrieve user registration requests for participant credentials.
It is also responsible for administrating requests as well as sending them to a central catalog upon approval.

## Build

If you only want to build the project, you can go to the root of the repository and run

```
./gradlew build
```

after which the built jar can be found at `backend/build/libs/backend-x.y.z.jar`.

## Run

The following environment variables can be set as needed.
Replace the right hand side with the actual values.
```
export SDCREATIONWIZARDAPI_BASEURL="SD Creation Wizard API base URL"
export SPRING_DATASOURCE_URL="Datasource URL"
export SPRING_DATASOURCE_USERNAME="Datasource Username"
export SPRING_DATASOURCE_PASSWORD="Datasource Password"
```

Through gradle:

```
./gradlew bootRun
```

Alternatively running the jar directly (if built previously):

```
java -jar backend/build/libs/backend-x.y.z.jar
```

Once the service is running, you can access it at http://localhost:8088/ .