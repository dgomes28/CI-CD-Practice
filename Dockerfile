FROM maven:3.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN mvn clean compile

CMD ["java", "-cp", "target/classes", "App"]