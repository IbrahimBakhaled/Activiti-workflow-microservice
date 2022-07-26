# Docker file for the Read Service
#
# Version 0.0.1

#jdk image
FROM adoptopenjdk/openjdk11:alpine-jre
USER root
# install

# label for the image
LABEL Description="Activiti workflow microservice" Version="1.0"

# the version of the archive
ARG VERSION=1.0

ARG CERT="relevebancaire-keystore.cer"

# mount the temp volume
VOLUME /tmp

COPY relevebancaire-docker-keystore.crt $JAVA_HOME/lib/security
RUN \
    cd $JAVA_HOME/lib/security \
    && keytool -importcert -alias relevebancaire-docker -storepass changeit -trustcacerts -noprompt -keystore cacerts -file relevebancaire-docker-keystore.crt
#    && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ldapcert -file ldap.cer
# Add the service as app.jar
ADD target/microservice-relevebancaire-activiti-workflow-${VERSION}-SNAPSHOT.jar app.jar

# touch the archive for timestamp
RUN sh -c 'touch /app.jar'

# entrypoint to the image on run
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
