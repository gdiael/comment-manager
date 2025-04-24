FROM openjdk:17

WORKDIR /app

COPY src ./src

RUN javac -d bin src/imd/ufrn/*.java

CMD ["java", "-cp", "bin", "imd.ufrn.Gateway"]
