# FROM openjdk:8
# EXPOSE 8888
# ADD /target/kaliber-config-server.jar kaliber-config-server.jar
# ENTRYPOINT ["java","-jar", "kaliber-config-server.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /configserver

WORKDIR /configserver

COPY pom.xml /configserver/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /configserver/src

# COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /configserver

WORKDIR /configserver

# Copy the built service jars and resources
COPY --from=webappserverbuild /configserver/target /configserver/target

RUN ls -ltr /configserver/

ENTRYPOINT ["java", "-jar", "target/kaliber-config-server.jar"]
