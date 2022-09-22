FROM maven:3.8.6-eclipse-temurin-17 AS builder
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY pom.xml $HOME
RUN mvn verify --fail-never
COPY src $HOME/src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /usr/app/target/distsys.jar /usr/local/lib/distsys.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev-docker","/usr/local/lib/distsys.jar"]
