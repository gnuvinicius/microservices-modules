#!/bin/bash
mvn versions:set -DnewVersion=staging
mvn -DskipTests clean package

docker build --no-cache \
  --build-arg APP_NAME=./target/kbn-api-staging \
  --build-arg POSTGRES_HOST=192.168.0.220 \
  --build-arg POSTGRES_PASSWD=2AkByM4NfHFkeJz --tag kbn-api:staging .
            
docker rm kbn-api -f
docker run -d --name kbn-api \
  --restart always \
  --network grg-net kbn-api:staging