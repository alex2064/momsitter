# momsitter

## 개발 환경

언어 : Kotlin 1.7.22 (Java Version 17)

프레임워크 : Spring Boot 3.0.6

database : MariaDB 10.11.2

Tool : IntelliJ IDEA Community Edition

사용자 인증 : JWT

## Database 접속 정보

src > main > resources > application.yml

    spring:
      datasource:
        driver-class-name: org.mariadb.jdbc.Driver
        url: jdbc:mariadb://localhost:3306/momsitter
        username: root
        password: 1234

## DDL

src > main > resources > sql > schema.sql