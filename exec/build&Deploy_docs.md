A. 사용한 JVM, WAS 제품 등의 종류와 설정 값, 버전(IDE버전 포함) 기재

- JVM : JDK 17
- WAS : Tomcat
- Web Server : Nginx/1.18.0 (Ubuntu)
- IDE
  - Frontend : VScode (1.86.1)
  - Backed : Intellij IDEA (2023.2.5)

B. 빌드 시 사용되는 환경변수 등의 내용 상세 기재

- application-env.yml (spring boot)

```
DB_USERNAME: root
DB_URL: jdbc:mysql://i10E108.p.ssafy.io:3306/umzip?serverTimezone=Asia/Seoul
DB_PASSWORD: 'umzip108'
JWT_SECRET_KEY: 'c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK'
JWT_ACCESS_EXPIRED_TIME_MS: '3600000000'  # 예시 값
S3_ACCESS_KEY: 'AKIAQVDAYCHVPXAWOA7C'
S3_SECRET_KEY: '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'
S3_BUCKET: 'umzip-service'
S3_REGION: 'ap-northeast-2'
REDIS_HOST: 'i10E108.p.ssafy.io'
REDIS_PASSWORD: '1234'
COOL_SMS_API_KEY: 'NCSSELUTJEWVTSDZ'
COOL_SMS_API_SECRET: 'DBNHZ7LONQ5OKSZGNROZX6VSO80UAKGF'
JWT_REFRESH_EXPIRED_TIME_MS: '21600000'
BUSINESS_SERVICE_KEY: 'YO1jqa68uhi%2Fs0DDrxlaJF6AErJZRopoNJsphblYM%2Fm5sfNYZLsuvvz9CessEvN9ZH1rMPD42xmIF23SVQUcXQ%3D%3D'
KAKAO_API_KEY: 'efbbf48d809a4e2001e31b17724e640c'
FUEL_API_KEY: 'F240126029'
MONGO_URI: 'mongodb://umzip108!:rkdalstn7221!@i10e108.p.ssafy.io:27017/umzip?authSource=umzip'

```

C. 배포 시 특이사항 기재

- Nginx를 활용해 리버스 프록시 구성
- 애플리케이션 서버에 메모리와 부하 등 간섭을 최소화하기 위해 CI/CD 서버와 애플리케이션, Openvidu 서버 분리
- publish over ssh를 활용해 빌드 후 SSH를 통해 원격 서버 배포 구현

D. DB 접속 정보 등 프로젝트(ERD)에 활용되는 주요 계정 및 프로퍼티가 정의된 파일 목록

```bash
jangha@naver.com / 1234 #  사용자 계정

umzip108@naver.com  / 1234 # 용달 및 청소 업체 계정
```
