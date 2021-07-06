[![Build Status](https://travis-ci.com/TechemyLtd/bnc-asset-re.svg?token=E336h1ZKxxzxrz3tKH8z&branch=master)](https://travis-ci.com/TechemyLtd/bnc-asset-re)

# Relay API Service

The asset re is a reference re which holds information directly relating to crypto and fiat assets.

## How tos

## Build & Test

This project uses gradle and uses the default tasks to compile and run unit tests. 

```bash
./gradlew clean assemble check
```

### Build and run locally on Docker
1. Build the docker container
```bash
./gradlew clean assemble check docker
```

2. Run Postgres
```bash
docker run --name postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_DB=marketdata postgres:9.6-alpine
```

3. Run the docker container 
```bash
docker run -e SPRING_PROFILES_ACTIVE=localhost -p 8080:8080 -i -t asset-re
```

### Build production equivalent container
```bash
./gradlew clean assemble check docker dockerTag -PTAG=$(git rev-parse --verify HEAD --short) -PREPOSITORY_URI=${DOCKER_REPO}${IMAGE_NAME}
```

### Profiling
To debug the container locally, the `JAVA_OPTS` environment variable can be provided when running the container.
```bash
docker run -p 8080:8080 -i -t -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005" asset-re
```



#### Recommended JAVA_OPTS
It is strongly recommended that the following Java Options are set when running the re in production.
```base
JAVA_OPTS="-XX:MaxRAMFraction=2 -XX:+UseG1GC -XX:+AlwaysPreTouch -XX:+UseStringDeduplication -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -javaagent:newrelic/newrelic.jar -Dnewrelic.environment=${environment} -Dnewrelic.config.file=newrelic/newrelic.yml -Djava.security.egd=file:/dev/./urandom"
```

## For more tasks run
```bash
./gradlew tasks
```

## Deployment

This project uses Terraform and the AWS CLI to deploy the re to the BNC ECS Cluster. To have the CI/CD pipeline deploy a re which has be deployed using a fork of this project you can follow the instructions below.



