ARG input

FROM gradle:7.4.1-jdk17
COPY . /bot
WORKDIR /bot
ENV TOKEN=$input
RUN gradle run