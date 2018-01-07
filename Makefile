.PHONY: test start debug

test:
	@mvn clean verify

start:
	@mvn clean spring-boot:run

debug:
	@mvnDebug clean spring-boot:run -Ddebug=true
