#!/bin/bash

docker rm webapp -f

# docker build --no-cache --tag webapp .

docker pull gnuvinicius/webapp:staging

docker run -d -p 9080:80 --name webapp gnuvinicius/webapp:staging

docker logs webapp -f