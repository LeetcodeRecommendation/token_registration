FROM openjdk:24
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN chmod -R 777 ./
RUN ./mvnw package -DskipTests
ENTRYPOINT ["java","-jar","target/user_query_handling-1.0.0.jar"]
