# POSSIBLE-X Portal Backend

TODO description

## Build

If you only want to build the project, you can go to the root of the repository and run

```
./gradlew build
```

after which the built jar can be found at `backend/build/libs/backend-x.y.z.jar`.

## Run

Through gradle:

```
./gradlew bootRun
```

Alternatively running the jar directly (if built previously):

```
java -jar backend/build/libs/backend-x.y.z.jar
```

Once the service is running, you can access it at http://localhost:8080/ .