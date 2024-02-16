## 목차

[Nginx 설치](#nginx-설치)

[Nginx 설정 파일 구성](#nginx-defaultconf-설정)

[frontend 코드 배포](#frontend-빌드-후-ubuntu로-dist-폴더-이동)

## nginx 설치

```bash
sudo apt update
sudo apt install nginx  # nginx 설치
sudo ufw app list # 방화벽 리스트
sudo ufw allow 'Nginx HTTP' # 방화벽 설정 허용
sudo ufw status # 상태 체크
```

## nginx default.conf 설정

```
server {
    root /home/ubuntu/frontend/dist;

	index index.html;
    server_name i10e108.p.ssafy.io; # managed by Certbot


	location / {
		try_files $uri $uri /index.html;
	}

	location /assets {
			alias /home/ubuntu/frontend/dist/assets;
	}

	location /api {
		proxy_pass "http://localhost:8080";
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header Host $http_host;

	}

	location /ws {
		proxy_pass "http://localhost:8080";
		proxy_http_version 1.1;
		proxy_set_header Upgrade $http_upgrade;
		proxy_set_header Connection "Upgrade";
		proxy_set_header Host $host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
	}

    listen [::]:443 ssl ipv6only=on; # managed by Certbot
    listen 443 ssl; # managed by Certbot
    ssl_certificate /etc/letsencrypt/live/i10e108.p.ssafy.io/fullchain.pem; # managed by Certbot
    ssl_certificate_key /etc/letsencrypt/live/i10e108.p.ssafy.io/privkey.pem; # managed by Certbot
    include /etc/letsencrypt/options-ssl-nginx.conf; # managed by Certbot
    ssl_dhparam /etc/letsencrypt/ssl-dhparams.pem; # managed by Certbot

}
server {
    if ($host = i10e108.p.ssafy.io) {
        return 301 https://$host$request_uri;
    } # managed by Certbot


	listen 80 ;
	listen [::]:80 ;
    server_name i10e108.p.ssafy.io;
    return 404; # managed by Certbot


}
```

## frontend 빌드 후 ubuntu로 dist 폴더 이동

### 1. fronted build

```cmd
npm install
npm run build
```

### 2. fileZilla 혹은 Termius를 사용해 로컬 빌드 dist 폴더를 ubuntu nginx root 경로로 이동

### 3. 도메인 접속하여 반영된 코드 확인
