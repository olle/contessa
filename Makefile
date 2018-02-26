.PHONY: test start start-maria debug

test:
	@mvn clean verify

start:
	@docker-compose up -d
	@mvn spring-boot:run -Dcontessa.base-dir=target/

start-maria:
	@docker-compose up -d mariadb
	@mvn spring-boot:run -Dcontessa.base-dir=target/ -Dspring.profiles.active=mariadb

start-minio:
	@docker-compose up -d minio
	@mvn spring-boot:run -Dcontessa.base-dir=target/ -Dspring.profiles.active=minio

start-cassandra:
	@docker-compose up -d cassandra
	@mvn spring-boot:run -Dcontessa.base-dir=target/ -Dspring.profiles.active=cassandra

start-redis:
	@docker-compose up -d redis
	@mvn spring-boot:run -Dcontessa.base-dir=target/ -Dspring.profiles.active=redis

debug:
	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=target/
