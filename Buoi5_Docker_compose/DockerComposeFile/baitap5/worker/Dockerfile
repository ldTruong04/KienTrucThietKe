FROM openjdk:17-slim

WORKDIR /app

# Download dependencies
RUN apt-get update && apt-get install -y wget && \
    wget https://repo1.maven.org/maven2/redis/clients/jedis/5.0.2/jedis-5.0.2.jar && \
    wget https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar && \
    wget https://repo1.maven.org/maven2/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar && \
    wget https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar && \
    wget https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.9/slf4j-simple-2.0.9.jar && \
    wget https://repo1.maven.org/maven2/org/apache/commons/commons-pool2/2.12.0/commons-pool2-2.12.0.jar && \
    wget https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar && \
    apt-get remove -y wget && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

COPY Worker.java .

RUN javac -cp "jedis-5.0.2.jar:json-20231013.jar:postgresql-42.6.0.jar" Worker.java

CMD ["java", "-cp", ".:jedis-5.0.2.jar:json-20231013.jar:postgresql-42.6.0.jar:slf4j-api-2.0.9.jar:slf4j-simple-2.0.9.jar:commons-pool2-2.12.0.jar:gson-2.10.1.jar", "Worker"]
