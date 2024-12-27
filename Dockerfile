# build spring-boot-sample

FROM maven:3.9 AS build

WORKDIR /build/spring-boot-sample

ADD . /build/spring-boot-sample

RUN mvn package

# run spring-boot-sample

FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS app

WORKDIR /app/spring-boot-sample

COPY --from=build /build/spring-boot-sample/target/*.jar /app/spring-boot-sample/spring-boot-sample.jar

CMD [ "java", "-jar", "spring-boot-sample.jar" ]
