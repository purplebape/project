FROM openjdk:17

COPY bot/target/bot-1.0-SNAPSHOT.jar app.jar
COPY application.yml /bot/src/main/resources/application.yml

RUN sed -i "s/{{BOT_TOKEN}}/${BOT_TOKEN}/g" /bot/src/main/resources/application.yml

ENTRYPOINT exec java --enable-preview -jar /app.jar
