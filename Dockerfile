FROM amazoncorretto:21

COPY build/libs/side-0.0.1.jar app.jar

VOLUME ["/data"]

CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]