# FROM openjdk:8
# EXPOSE 8761
# ADD /target/kaliber-naming-server.jar kaliber-naming-server.jar
# ENTRYPOINT ["java","-jar", "/kaliber-naming-server.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /namingserver

WORKDIR /namingserver

COPY pom.xml /namingserver/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /namingserver/src

#COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /namingserver

WORKDIR /namingserver

# Copy the built service jars and resources
COPY --from=webappserverbuild /namingserver/target /namingserver/target

RUN ls -ltr /namingserver/

ENTRYPOINT ["java", "-jar", "target/kaliber-naming-server.jar"]
