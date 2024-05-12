#!/bin/bash

docker rm webapp -f

docker build --no-cache --tag webapp .

docker run -d -p 9080:80 --name webapp gnuvinicius/webapp:staging

docker logs webapp -f