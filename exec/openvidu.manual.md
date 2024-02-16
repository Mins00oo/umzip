## 목차

 - [EC2 사양 및 환경](#EC2-사양-및-환경)

 - [1. 환경 구성](#1.-환경-구성)

 - [2. OpenVidu-Call 배포](#2.-OpenVidu--Call-배포)

 - [3. OpenVidu 설정 변경 및 실행](#3.-OpenVidu-설정-변경-및-실행)
 
 - [참고 문서](#참고-문서)



<br><br><br><br>

 ## EC2 사양 및 환경

  - t3.large(vCPU 2, Memory 8GB), 30GB
  - Ubuntu 20.04 LTS
  - ElasticIP 할당
  - 개인 도메인(mfdo.site) 할당

 \* **OpenVidu 최소사양** : CPU 2개, RAM 8GB 

<br><br><br><br>


## 1. 환경 구성

### [ Docker Install ]

1. 시스템 업데이트

```sh
sudo apt update
```
<br><br>

2. 필요한 의존성 패키지 설치

20.04 버전에 맞도록 링크 이동하여 아래 파일들에 대해 최신 버전으로 선정하였다.
https://download.docker.com/linux/ubuntu/dists/focal/pool/stable/amd64/

 - `containerd.io_<version>_<arch>.deb`
 - `docker-ce_<version>_<arch>.deb`
 - `docker-ce-cli_<version>_<arch>.deb`
 - `docker-buildx-plugin_<version>_<arch>.deb`
 - `docker-compose-plugin_<version>_<arch>.deb`

 ```sh
 sudo dpkg -i ./containerd.io_1.2.13-2_amd64.deb \
  ./docker-ce_19.03.10~3-0~ubuntu-focal_amd64.deb \
  ./docker-ce-cli_19.03.10~3-0~ubuntu-focal_amd64.deb \
  ./docker-buildx-plugin_0.10.2-1~ubuntu.20.04~focal_amd64.deb \
  ./docker-compose-plugin_2.10.2~ubuntu-focal_amd64.deb
  ```
도커 데몬에 대해서 자동 실행 될 것이다.

<br><br>

3. 서비스 실행

```sh
sudo service docker start
```

<hr>

### [ Docker Compose Install ]

1. docker compose 설치

```sh
sudo apt-get install docker-compose-plugin
```

2. 설치확인

```sh
docker compose version
```

<br><br><br><br>

## 2. OpenVidu-Call 배포

1. 관리자 권한 획득

```sh
sudo su
```

2. 권장 설치 폴더 이동

```sh
cd /opt
```

3. OpenVidu 배포파일 다운로드

```sh
curl https://s3-eu-west-1.amazonaws.com/aws.openvidu.io/install_openvidu_latest.sh | bash
```

<br><br>


## 3. OpenVidu 설정 변경 및 실행

1. 경로 이동

```
cd openvidu
```

2. `.env`파일 편집

- 내가 구매한 도메인을 이용할 것이고, SSL 인증은 letsencrypt이용
```sh
DOMAIN_OR_PUBLIC_IP=mfdo.site

OPENVIDU_SECRET=MY_SECRET

CERTIFICATE_TYPE=letsencrypt
```

3.OpenVidu의 미디어, 백, 프록시, 프론트 서버가 구동된다.

```
./openvidu start
```


<br><br><br><br>

## 참고 문서

 - [Docker 공식 문서](https://docs.docker.com/engine/install/ubuntu/)
 - [Docker Compose 공식 문서](https://docs.docker.com/compose/install/)
 - [OpenVidu 공식 문서](https://docs.openvidu.io/en/stable/deployment/ce/on-premises/)
