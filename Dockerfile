FROM gradle:jdk21-ubi-minimal AS build
WORKDIR /app-build
COPY . .
RUN gradle clean bootJar

FROM openjdk:21
WORKDIR /server-app
COPY --from=build /app-build/build/libs/*.jar server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "server.jar"]