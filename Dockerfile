FROM maven:3.8.6-eclipse-temurin-17 AS builder
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder /home/app/target/distsys.jar /usr/local/lib/distsys.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/distsys.jar"]
