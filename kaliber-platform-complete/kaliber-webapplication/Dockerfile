# FROM openjdk:8
# EXPOSE 8092
# ADD /target/kaliber-webapplication.jar kaliber-webapplication.jar
# ENTRYPOINT ["java","-jar", "kaliber-webapplication.jar"]

# FROM openjdk:8
# EXPOSE 8092
# ADD /target/kaliber-webapplication.jar kaliber-webapplication.jar
# ENTRYPOINT ["java","-jar", "kaliber-webapplication.jar"]
#-----------------------------#
#          Java Dependency    #
FROM maven:3.6-jdk-11 as webapp_server_maven_cache

RUN mkdir /webapplication

WORKDIR /webapplication

COPY pom.xml /webapplication/

RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline
# ----------------------------#
#         UI BUILD            #
# ----------------------------#
FROM node:12-alpine as webappbuild

RUN npm install -g @angular/cli

RUN mkdir /webapp

WORKDIR /webapp

COPY kaliber-webapp/package.json /webapp/

RUN ls -ltr

RUN npm install

#RUN echo "WORK SUCCESSFULL
COPY ./kaliber-webapp /webapp/
#RUN ng build --prod --delete-output-path --output-path=/webapp/dist

RUN npm run build --prod --delete-output-path --output-path=/webapp/dist

RUN ls -ltr /webapp/dist
#RUN cd dist && ls -ltr

# ----------------------------#
#        JAVA BUILD           #
# ----------------------------#
FROM maven:3.6-jdk-12 as webappserverbuild

RUN mkdir /webapplication

WORKDIR /webapplication

COPY pom.xml /webapplication/
# Expected to download dependencies but not build
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO dependency:go-offline
# Now copy actual source, i.e., this line should be after downloading dependencies to avoid repeated download when src changes
COPY ./src /webapplication/src

RUN rm -rf /webapplication/src/main/resources/static

RUN mkdir -p /webapplication/src/main/resources/static

# Uncomment the below line. Only for testing
#COPY --from=webapp_server_maven_cache /root/.m2 /root/.m2

# Now copy webapp (webclient) distribution for packaging with JAR
COPY --from=webappbuild /webapp/dist/* /webapplication/src/main/resources/static/

# Build now
RUN mvn -Dmaven.repo.local=$MVN_LOCAL_REPO clean package -DskipTests=true
# If you choose to test it in a debug mode, this will be the end of Dockerfile
# ENTRYPOINT ["mvn", "spring-boot:run"]
# ----------------------------#
#        JAVA Service         #
# ----------------------------#
FROM maven:3.6-jdk-12
# name this basis the micro service name
RUN mkdir /webapplication
WORKDIR /webapplication
# Copy the built service jars and resources
COPY --from=webappserverbuild /webapplication/target /webapplication/target
RUN ls -ltr /webapplication/
RUN ls -ltr /webapplication/target
ENTRYPOINT ["java", "-jar", "target/kaliber-webapplication.jar"]