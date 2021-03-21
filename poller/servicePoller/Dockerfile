FROM openjdk:8-jre-alpine

ENV VERTICLE_FILE servicePoller-1.0.0-SNAPSHOT-fat.jar      

# Set the location of the verticles
ENV VERTICLE_HOME /usr/verticles

# Copy your fat jar to the container
COPY build/libs/$VERTICLE_FILE $VERTICLE_HOME/                          

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar $VERTICLE_FILE"]  