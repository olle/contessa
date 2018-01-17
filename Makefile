.PHONY: test start start-maria debug

test:
	@mvn clean verify

start:
	@mvn clean spring-boot:run -Dcontessa.base-dir=target/

start-maria:
	@mvn clean spring-boot:run -Dcontessa.base-dir=target/ -Dspring.profiles.active=mariadb

debug:
	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=target/
