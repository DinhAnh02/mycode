FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /src
COPY . .
RUN mvn install -DskipTests=true

FROM openjdk:21-jdk

RUN unlink /etc/localtime; ln -s /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime

# Copy file JAR từ build stage
COPY --from=build src/target/vks-be-0.0.1-SNAPSHOT.jar /run/vks-be-0.0.1-SNAPSHOT.jar

# Copy file app_usb.zip từ build stage (nếu có)
COPY --from=build src/AppUsb/app_usb.zip /run/AppUsb/app_usb.zip

EXPOSE 8081

ENV JAVA_OPTIONS="-Xmx2048m -Xms256m"
ENTRYPOINT ["java", "-jar", "/run/vks-be-0.0.1-SNAPSHOT.jar"]
