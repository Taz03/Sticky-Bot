ARG TOKEN

FROM alpine

WORKDIR /opt/bot

COPY build/libs/Sticky-Bot-all.jar .

ENV TOKEN=$TOKEN

RUN apk --no-cache add openjdk17-jre-headless
RUN apk --no-cache add ca-certificates
RUN ./gradlew shadowJar

CMD [ "java", "-jar", "Sticky-Bot-all.jar" ]