# ---- Stage 1: Build ----
# Using a full JDK+Maven image only for the build stage keeps the final
# image small — none of this layer ends up in the runtime image.
FROM maven:3.9-eclipse-temurin-26 AS build
WORKDIR /workspace

# Copy pom first so Docker can cache the dependency layer between builds
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Stage 2: Runtime ----
# Distroless-style slim JRE image: no shell, no package manager, smaller
# attack surface — this is also what keeps the "Image Scanning" step in
# CI fast and the vulnerability count low.
FROM eclipse-temurin:26-jre-alpine
WORKDIR /app

# Run as non-root — required by most EKS pod security standards
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /workspace/target/demo-app-*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
