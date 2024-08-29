FROM openjdk:17-jdk-slim
WORKDIR /desafio-rpa
COPY drivers/chromedriver /usr/local/bin/chromedriver
RUN chmod +x /usr/local/bin/chromedriver
COPY out/artifacts/desafio_rpa_jar/desafio-rpa.jar /app/desafio-rpa.jar
RUN chmod +x /app/desafio-rpa.jar
ENTRYPOINT ["java", "-jar", "/app/desafio-rpa.jar"]
