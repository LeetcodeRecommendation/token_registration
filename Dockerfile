FROM openjdk:24
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN ./mvnw package -DskipTests
ENTRYPOINT ["java","-jar","target/token_registration-1.0.0.jar"]
