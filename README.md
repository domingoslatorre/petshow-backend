# petshow-backend


## Como executar usando Docker Compose

### 1. Antes de executar o docker compose fazer o build do projeto gerando o artefato `build/libs/petshow-backend-0.0.1-SNAPSHOT.jar`
```
./gradlew build
```

### 2. Executar o comando docker-compose up
```
docker-compose up --build
```
Serviços locais criados após a execução do comando `docker-compose up --build:

- MySQL 5.7
    - SGBD utilizado no projeto
    - localhost:3306
- phpMyAdmin 
    - MySQL client para debug local
    - localhost:8081
- mailhog 
    - Fake SMTP server para teste
    - localhost:587 (smpt server)
    - localhost:8082 (web ui para verificar os e-mails recebidos)
- petshow-backend
    - Applicação
    - localhost:8080

    
3. Para parar todos os serviços
```
docker-compose down -v
```