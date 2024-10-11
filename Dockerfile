FROM ubuntu:20.04

WORKDIR /system_stats

ENV TZ=Asia/Kolkata \
    DEBIAN_FRONTEND=noninteractive

EXPOSE 3000 8080

RUN apt update && apt install -y \
        openjdk-17-jdk \
        openjdk-17-jre \
        curl \
        lsb-release \
        ca-certificates

RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
              apt-get install -y nodejs

RUN apt update && apt install -y maven

COPY . .
RUN cd /system_stats/Frontend/ && \  
        npm install

RUN cd /system_stats/Backend && mvn clean package

CMD sh -c "cd /system_stats/Backend/target && java -jar system-stats-backend.jar & sleep 5  && cd /system_stats/Frontend && npm start --port 3000"

# CMD ["sh", "-c", "cd /system_stats/Backend && java -jar system-stats-backend.jar"]
# CMD ["sh", "-c", "cd /system_stats/Frontend && npm start --port 3000"]
