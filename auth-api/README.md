# Auth API


## rodar database no docker

```
docker run -d --name postgres -p 5432:5432  --restart always -e POSTGRES_PASSWORD=2AkByM4NfHFkeJz -v ./pgdata:/var/lib/postgresql/data postgres:15.5-alpine3.19 
```

criar a base de dados no postgreSQL:
```
$ docker exec -it postgres /bin/bash

$ su postgres

$ psql

# create database "garage-auth";
```


## Para rodar a aplicação local

### adicionar variavel de ambiente no linux

arquivo .bashrc

```
export JAVA_HOME=/home/vinicius/env/jdk-17.0.2

export M2_HOME=/home/vinicius/env/apache-maven-3.9.6

export JWT_SECRET=pdkBtv9driN4lSGTNeOfBK6p5IC6iz

export POSTGRES_HOST=localhost

#export POSTGRES_HOST=168.138.128.121

export POSTGRES_DATABASE=garage-auth

export POSTGRES_USER=postgres

export POSTGRES_PASSWD=2AkByM4NfHFkeJz

export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
```

### rodar aplicação local

```
mvn spring-boot:run
```


## Para rodar a aplicação no docker


executar o script docker-run.sh

```
chmod +x docker-run.sh

./docker-run.sh
```