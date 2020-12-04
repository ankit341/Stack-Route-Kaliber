# FROM openjdk:8
# EXPOSE 8091
# ADD /target/kaliber-question-inventory.jar kaliber-question-inventory.jar
# ENTRYPOINT ["java","-jar", "kaliber-question-inventory.jar"]

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /questioninventory

WORKDIR /questioninventory

COPY pom.xml /questioninventory/

# Expected to download dependencies but not build
RUN mvn dependency:go-offline

# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /questioninventory/src


# Build now
RUN mvn clean package -DskipTests=true

# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12

# name this basis the micro service name
RUN mkdir /questioninventory

WORKDIR /questioninventory

# Copy the built service jars and resources
COPY --from=webappserverbuild /questioninventory/target /questioninventory/target

RUN ls -ltr /questioninventory/

ENTRYPOINT ["java", "-jar", "target/kaliber-question-inventory.jar"]