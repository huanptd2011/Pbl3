# Sử dụng JDK 21 làm base image
FROM maven:3.9-eclipse-temurin-21 AS builder

# Thiết lập thư mục làm việc
WORKDIR /app

# Copy file Maven để cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy toàn bộ source code
COPY . .

# Build ứng dụng
RUN mvn clean package -DskipTests

# Chạy ứng dụng với OpenJDK runtime
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy file JAR từ bước build
COPY --from=builder /app/target/*.jar app.jar

# Expose cổng ứng dụng
EXPOSE 8080

# Chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
