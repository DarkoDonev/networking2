FROM eclipse-temurin
WORKDIR /tcp/server
COPY . .
RUN javac Worker.java Server.java
EXPOSE 4502
ENTRYPOINT ["java", "Server.java"]