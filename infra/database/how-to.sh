#!/bin/bash

#fonte: https://refine.dev/blog/postgres-on-kubernetes/#introduction

docker build -t grg-db-psql:15.5 .

echo _ | docker login -u gnuvinicius --password-stdin

docker push gnuvinicius/grg-db-psql:15.5