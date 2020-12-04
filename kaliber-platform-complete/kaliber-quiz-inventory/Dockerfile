# FROM openjdk:8
# EXPOSE 8090
# ADD /target/kaliber-quiz-inventory.jar kaliber-quiz-inventory.jar
# ENTRYPOINT ["java","-jar", "kaliber-quiz-inventory.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /quizinventory

WORKDIR /quizinventory

COPY pom.xml /quizinventory/

# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /quizinventory/src

#COPY /m2 /root/.m2
# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /quizinventory

WORKDIR /quizinventory

# Copy the built service jars and resources
COPY --from=webappserverbuild /quizinventory/target /quizinventory/target

RUN ls -ltr /quizinventory/

ENTRYPOINT ["java", "-jar", "target/kaliber-quiz-inventory.jar"]