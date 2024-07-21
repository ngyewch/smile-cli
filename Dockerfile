FROM docker.io/library/eclipse-temurin:21

WORKDIR /smile-cli

COPY . .
RUN ./gradlew installDist

ENTRYPOINT ["build/install/smile-cli/bin/smile-cli"]
