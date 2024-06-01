FROM eclipse-temurin
WORKDIR /tcp/client
COPY . .
RUN javac Client.java

ENTRYPOINT ["java", "Client.java"]