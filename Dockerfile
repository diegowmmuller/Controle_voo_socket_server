# =============================
# Etapa 1 - Build
# =============================
FROM maven:3.9.9-eclipse-temurin-21 AS build

# Diretório de trabalho dentro do container
WORKDIR /app

# Copiar pom.xml e baixar dependências primeiro (para cache melhor)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar o restante do código fonte
COPY src ./src

# Compilar e empacotar o projeto (sem rodar testes)
RUN mvn clean package -DskipTests


# =============================
# Etapa 2 - Runtime
# =============================
FROM eclipse-temurin:21.0.3_9-jdk

WORKDIR /app

# Copiar apenas o JAR final do build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 3000 para conexões
EXPOSE 3000

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]