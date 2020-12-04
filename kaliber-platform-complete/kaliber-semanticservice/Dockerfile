# FROM openjdk:8
# EXPOSE 8090
# ADD /target/kaliber-semanticservice.jar kaliber-semanticservice.jar
# ENTRYPOINT ["java","-jar", "kaliber-semanticservice.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /semanticservice

WORKDIR /semanticservice

COPY pom.xml /semanticservice/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /semanticservice/src

#COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /semanticservice

WORKDIR /semanticservice

# Copy the built service jars and resources
COPY --from=webappserverbuild /semanticservice/target /semanticservice/target

RUN ls -ltr /semanticservice/

ENTRYPOINT ["java", "-jar", "target/kaliber-semanticservice-0.0.1-SNAPSHOT.jar"]
