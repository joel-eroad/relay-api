FROM adoptopenjdk:11.0.7_10-jre-hotspot-bionic

RUN groupadd -g 999 appuser && useradd -r -u 999 -g appuser appuser
USER appuser

EXPOSE 8080

COPY *.jar app.jar

ENTRYPOINT exec java -XX:+UseG1GC -XX:MaxRAMPercentage=80 -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -XX:+UnlockExperimentalVMOptions -jar app.jar
#ENTRYPOINT ["java","-jar","-Dspring.datasource.url=jdbc:mysql://localhost:3306/relay","-Dspring.datasource.username=root","-Dspring.datasource.password=password", "-Dspring.profiles.active=localhost", "app.jar"]

HEALTHCHECK --start-period=300s \
  CMD curl --silent --fail --max-time 30 http://localhost:8080/actuator/health || exit 1
