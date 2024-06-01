FROM eclipse-temurin
WORKDIR /tcp/logger
COPY . .
RUN javac Logger.java Worker.java
EXPOSE 7001
ENTRYPOINT ["java", "Logger.java"]