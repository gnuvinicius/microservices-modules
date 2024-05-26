#!/bin/bash
# mvn versions:set -DnewVersion=staging
# mvn -DskipTests clean package
mvn -DskipTests clean package

docker build --no-cache \
  --build-arg APP_NAME=./target/gateway-0.0.1-SNAPSHOT \
  --tag gateway:staging .
            
docker rm gateway -f
docker run -d \
  # --restart always \
  --network grg-net \
  -p 8083:8083 \
  --name gateway gateway:staging