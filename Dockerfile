FROM gradle:6.5.1-jdk14 AS build
COPY --chown=gradle:gradle . /stockvipalerts
WORKDIR /stockvipalerts
RUN gradle shadowJar --no-daemon

FROM openjdk:11.0.8-jre-slim
RUN mkdir /config/
COPY --from=build /stockvipalerts/build/libs/*.jar /

ENTRYPOINT ["java", "-jar", "/StockVIPAlerts.jar"]