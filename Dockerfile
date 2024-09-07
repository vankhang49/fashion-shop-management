# Build stage
FROM gradle:7.6.1-jdk17 AS build
WORKDIR /app

# Install JDK 21
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://download.oracle.com/java/21/latest/jdk-21_linux-x64_bin.tar.gz && \
    tar -xvf jdk-21_linux-x64_bin.tar.gz && \
    mv jdk-21.0.4 /usr/local/jdk-21 && \
    update-alternatives --install /usr/bin/java java /usr/local/jdk-21/bin/java 1 && \
    update-alternatives --set java /usr/local/jdk-21/bin/java && \
    java -version

COPY . .
RUN gradle clean build -x test

# Run stage

FROM openjdk:21-jdk-slim
WORKDIR /app

COPY --from=build /app/build/libs/fashion-shop-0.0.1-SNAPSHOT.jar drcomputer.jar
EXPOSE 8080

ENTRYPOINT ["java","-jar","drcomputer.jar"]
