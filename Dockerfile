# Bot token
ARG TOKEN

FROM gradle:7.5.0-jdk18-alpine

ENV TOKEN=$TOKEN

# Copy all the files to the container
COPY . .

# Create the shadow jar
RUN chmod +x gradlew && ./gradlew shadowJar

# Run the shadow jar
CMD [ "java", "-jar", "build/libs/Sticky-Bot-all.jar" ]
