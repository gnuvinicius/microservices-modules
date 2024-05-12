# Auth API


## rodar database no docker

```bash
foo@bar:~$ docker run -d --name postgres -p 5432:5432  --restart always -e POSTGRES_PASSWORD=2AkByM4NfHFkeJz -v ./pgdata:/var/lib/postgresql/data postgres:15.5-alpine3.19 
```

criar a base de dados no postgreSQL:
```bash
foo@bar:~$ docker exec -it postgres /bin/bash
foo@bar:~$ su postgres
foo@bar:~$ psql
```

```sql
CREATE DATABASE auth_d;
CREATE DATABASE kbn_d;
```

## Para rodar a aplicação local

### adicionar variavel de ambiente no linux

arquivo .bashrc

```bash
export AUTH_DATABASE=auth_d
export KBN_DATABASE=kbn_d
export JWT_SECRET=pdkBtv9driN4lSGTNeOfBK6p5IC6iz
export POSTGRES_HOST=127.0.0.1
export POSTGRES_PASSWD=2AkByM4NfHFkeJz
export POSTGRES_USER=postgres

export PATH=$PATH:$JAVA_HOME/bin:$M2_HOME/bin
```

### rodar aplicação local

```bash
foo@bar:~$ mvn spring-boot:run
```
