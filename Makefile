.PHONY: test start debug

test:
	@mvn clean verify

start:
	@mvn clean spring-boot:run -Dcontessa.base-dir=target/

debug:
	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=target/

