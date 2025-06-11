FROM gradle:jdk21-ubi-minimal AS build
WORKDIR /app-build
COPY . .
RUN gradle clean bootJar

FROM amazoncorretto:21-alpine
RUN apk add --no-cache curl
WORKDIR /server-app
COPY --from=build /app-build/build/libs/*.jar server.jar
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh
EXPOSE 8080
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENTRYPOINT sh -c "./wait-for-it.sh $DB_HOST:$DB_PORT -- java -jar server.jar"
