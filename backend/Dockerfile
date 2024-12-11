FROM gradle:8.9.0-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
RUN apt-get install -y wget
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y ./google-chrome-stable_current_amd64.deb
WORKDIR /home/gradle/src
RUN gradle build -PnpmEnv=dev --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8088

RUN mkdir /app

COPY --from=build /home/gradle/src/backend/build/libs/*.jar /app/backend.jar
ARG VERSION_NO="unknown"
ENV VERSION_NO=$VERSION_NO
ARG VERSION_DATE="unknown"
ENV VERSION_DATE=$VERSION_DATE
ENTRYPOINT ["java","-jar","/app/backend.jar"]
