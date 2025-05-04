FROM eclipse-temurin:24-jdk

WORKDIR /app

# Copiar o arquivo JAR do projeto
COPY target/*.jar app.jar

COPY src/main/resources/ resources/

# Porta em que a aplicação Spring Boot vai rodar
EXPOSE 8080

# Configurações da JVM para JDK 24
ENV JAVA_OPTS="-XX:+UseZGC -XX:+UseContainerSupport"

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]