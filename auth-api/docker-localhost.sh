#!/bin/bash
mvn versions:set -DnewVersion=staging
mvn -DskipTests clean package

docker build --no-cache \
  --build-arg APP_NAME=./target/auth-api-staging \
  --build-arg POSTGRES_HOST=192.168.0.220 \
  --build-arg POSTGRES_PASSWD=2AkByM4NfHFkeJz --tag auth-api:staging .
            
docker rm auth-d -f
docker run -d -p 8080:8080 --name auth-d auth-api:staging