# Bot token
ARG TOKEN

# Postgresql info
ARG PGHOST
ARG PGPORT
ARG PGDATABASE
ARG PGUSER
ARG PGPASSWORD

FROM gradle:7.4.2-jdk18-alpine

ENV TOKEN=$TOKEN
ENV PGHOST=$PGHOST
ENV PGPORT=$PGPORT
ENV PGDATABASE=$PGDATABASE
ENV PGUSER=$PGUSER
ENV PGPASSWORD=$PGPASSWORD

# Copy all the files to the container
COPY . ./

# Create the jar
RUN ./gradlew shadowJar

# Run the jar
CMD [ "java", "-jar", "build/libs/Sticky-Bot-all.jar" ]