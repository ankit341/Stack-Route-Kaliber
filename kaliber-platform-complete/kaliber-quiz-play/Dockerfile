# FROM openjdk:8
# EXPOSE 8070
# ADD /target/kaliber-quiz-play.jar kaliber-quiz-play.jar
# ENTRYPOINT ["java","-jar", "kaliber-quiz-play.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /quizplay

WORKDIR /quizplay

COPY pom.xml /quizplay/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /quizplay/src

#COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /quizplay

WORKDIR /quizplay

# Copy the built service jars and resources
COPY --from=webappserverbuild /quizplay/target /quizplay/target

RUN ls -ltr /quizplay/

ENTRYPOINT ["java", "-jar", "target/kaliber-quiz-play.jar"]