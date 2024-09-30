FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR ./src
COPY . .
RUN mvn install -DskipTests=true

RUN ls -l /src/AppUsb

FROM openjdk:21-jdk

RUN unlink /etc/localtime;ln -s  /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime
COPY --from=build src/target/vks-be-0.0.1-SNAPSHOT.jar /run/vks-be-0.0.1-SNAPSHOT.jar

COPY --from=build /src/AppUsb /src/AppUsb
RUN ls -l /src/AppUsb

EXPOSE 8081

ENV JAVA_OPTIONS="-Xmx2048m -Xms256m"
ENTRYPOINT java -jar $JAVA_OPTIONS /run/vks-be-0.0.1-SNAPSHOT.jar