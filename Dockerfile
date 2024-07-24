FROM docker.io/library/eclipse-temurin:21

WORKDIR /smile-cli

COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle.kts .
RUN ./gradlew

COPY . .
RUN ./gradlew installDist

ENTRYPOINT ["build/install/smile-cli/bin/smile-cli"]
