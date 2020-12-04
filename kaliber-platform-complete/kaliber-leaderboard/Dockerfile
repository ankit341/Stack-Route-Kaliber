# FROM openjdk:8
# EXPOSE 8092
# ADD /target/kaliber-user-management.jar kaliber-user-management.jar
# ENTRYPOINT ["java","-jar", "kaliber-user-management.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /leaderboard

WORKDIR /leaderboard

COPY pom.xml /leaderboard/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /leaderboard/src

#COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /leaderboard

WORKDIR /leaderboard

# Copy the built service jars and resources
COPY --from=webappserverbuild /leaderboard/target /leaderboard/target

RUN ls -ltr /leaderboard/

ENTRYPOINT ["java", "-jar", "target/kaliber-leaderboard.jar"]