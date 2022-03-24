ARG TOKEN

FROM gradle:7.4.1-jdk17
COPY . /bot
WORKDIR /bot
RUN gradle run