## Microservices archicture studies

Projeto com o intuito de estudos sobre microservices e devops, utilizando Spring boot 3.2.6 e Spring Cloud. 

- Spring Boot (3.2.6)
- OpenJDK 17
- Spring Security
- Flyway (Migration)
- Kubernetes
- GitHub Actions

### rodar o database no docker

```bash
foo@bar:~$ docker run -d --name postgres -p 5432:5432  --restart always -e POSTGRES_PASSWORD=2AkByM4NfHFkeJz -v ./pgdata:/var/lib/postgresql/data postgres:16.3-alpine3.20
```
tem o script no diretorio ./infra/database/dev para rodar esse comando.


criar a base de dados no postgreSQL:
```bash
docker exec -it postgres /bin/bash
```
```bash
su postgres
psql
```

```sql
CREATE DATABASE auth_d;
CREATE DATABASE kbn_d;
```

### Para rodar a aplicação local

```bash
docker network create grg-net
```

Todos os services tem um script docker-localhost.sh

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

### Desativar o IPv6
se precisar Desativar o IPv6 da VM que está rodando o github self-runner

```bash
sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1
sudo sysctl -w net.ipv6.conf.lo.disable_ipv6=1
sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1
```
