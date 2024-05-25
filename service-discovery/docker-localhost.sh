#!/bin/bash
# mvn versions:set -DnewVersion=staging
# mvn -DskipTests clean package
mvn -DskipTests clean package

docker build --no-cache \
  --build-arg APP_NAME=./target/service-discovery-0.0.1-SNAPSHOT \
  --tag service-discovery:staging .
            
docker rm service-discovery -f
docker run -d --restart always -p 8761:8761 \
 --name service-discovery service-discovery:staging