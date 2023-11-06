FROM openjdk:17
WORKDIR /app

ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY day-events-plugin-*.jar ./
CMD java -Dspring.profiles.active=prod -Dspring.config.location=config/application-prod.yaml -jar day-events-plugin-*.jar