FROM ubuntu:20.04

WORKDIR /system_stats

RUN DEBIAN_FRONTEND=noninteractive

ENV TZ=Asia/Kolkata \
    DEBIAN_FRONTEND=noninteractive

RUN apt update && apt install -y \
        openjdk-17-jdk \
        openjdk-17-jre \
        curl \
        lsb-release \
        ca-certificates

RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
              apt-get install -y nodejs

COPY . .
RUN cd /system_stats/Frontend/ && \  
        npm install

RUN apt update && apt install -y maven

RUN cd /system_stats/Backend && mvn clean package

EXPOSE 3000 8080

# CMD ["sh", "-c", "cd /system_stats/System-Stats-Frontend && npm start --port 3000"]


#java -jar Backend/target/system-stats-backend.jar
# FROM openjdk:17-jdk-slim
# WORKDIR /app
# COPY target/system-stats-backend.jar system-stats-backend.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "system-stats-backend.jar"]