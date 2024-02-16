## 목차

[ubuntu에 Docker 및 docker-compose 설치](#ec2-docker--docker-compose-설치)

[spring boot 배포](#spring-boot-배포)

[database 실행 (mysql, redis, mongoDB)](#database-설치-및-실행)

## EC2 Docker & docker-compose 설치

### 1. Docker 설치

```bash
# 1. 오래된 기존 버전이 있는지 확인
sudo apt-get remove docker docker-engine docker.io containerd runc

# 2. repository 설정
sudo apt-get update

sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release

# 3. docker의 offical gpc key 등록
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

# 4. stable repository 등록
echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# 5. docker engine 설치
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io

# 6. 도커 버전 확인
docker --version
```

### 2. docker-compose 설치

```bash
# 1. 도커 컴포즈 설치 (원하는 버전이 있다면 2.5.0대신 버전 명시
sudo curl -L "https://github.com/docker/compose/releases/download/v2.5.0/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

# 2. 실행 권한 부여
sudo chmod +x /usr/local/bin/docker-compose

# 3. 설치 확인
docker-compose --version
```

<br>
<br>

## Spring boot 배포

### 1. Dockerfile

```
FROM openjdk:17
ARG JAR_FILE=build/libs/umzip-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app/umzip-0.0.1-SNAPSHOT.jar
COPY src/main/resources/application-env.yml /app/application-env.yml
WORKDIR /app
ENTRYPOINT ["java","-jar","umzip-0.0.1-SNAPSHOT.jar"]
```

### 2. docker-compose.yml

```
version: "3"
services:
  umzip:
    container_name: backend
    build:
      context: ./
      dockerfile: Dockerfile
    volumes:
      - ./src/main/resources:/src/main/resources
    ports:
      - "8080:8080"
    restart: always

```

### 3. .jar 파일 추출 후 docker build

```bash
./gradlew clean build # jar 파일 추출
# build/libs 경로에 jar파일이 있는지 확인 후 docekr-compose가 위치한 경로로 이동

docker-compose up --build -d # 도커 이미지 생성 후 빌드
```

<br>
<br>

## Database 설치 및 실행

### 1. MySQL 설치

```bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=umzip108 -d -p 3306:3306 mysql:8.0.33
```

### 2. redis 실행을 위한 docker-compose.yml

```bash
version: '3.7'

services:
    redis:
        container_name: redis-server
        image: redis:alpine
        command: redis-server --requirepass 1234 --port 6379
        hostname: redis-server
        restart: always
        ports:
          - 6379:6379


# 위 파일 저장 후 아래 코드 실행
docker-compose up --build -d
```

### 3. mongoDB 실행

```bash
docker run --name mongodb-container -v ~/data:/data/db -d -p 27017:27017 mongo
# mongoDB에 접속하여 권한 계정 생성 필요
```
