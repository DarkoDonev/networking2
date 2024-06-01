FROM eclipse-temurin
WORKDIR /tcp/logger
COPY . .
RUN javac Worker.java ClientLogger.java
EXPOSE 7502
ENTRYPOINT ["java", "ClientLogger.java"]