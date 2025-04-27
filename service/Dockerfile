FROM openjdk:24-jdk-slim

WORKDIR /app

COPY src ./src

RUN javac -d bin src/imd/ufrn/*.java

CMD ["java", "-cp", "bin", "imd.ufrn.Service"]
