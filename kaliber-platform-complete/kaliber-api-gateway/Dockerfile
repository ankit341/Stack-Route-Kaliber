# FROM openjdk:8
# EXPOSE 8765
# ADD /target/kaliber-api-gateway.jar kaliber-api-gateway.jar
# ENTRYPOINT ["java","-jar", "kaliber-api-gateway.jar"]


# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /apigateway

WORKDIR /apigateway

COPY pom.xml /apigateway/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /apigateway/src

# COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /apigateway

WORKDIR /apigateway

# Copy the built service jars and resources
COPY --from=webappserverbuild /apigateway/target /apigateway/target

RUN ls -ltr /apigateway/

ENTRYPOINT ["java", "-jar", "target/kaliber-api-gateway.jar"]
