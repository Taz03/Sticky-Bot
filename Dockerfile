ARG TOKEN

FROM alpine

WORKDIR /opt/bot

COPY build/libs/Sticky-Bot-all.jar .

RUN apk --no-cache add openjdk17-jre-headless
RUN apk --no-cache add ca-certificates

ENV TOKEN=$TOKEN

CMD [ "java", "-jar", "Sticky-Bot-all.jar" ]