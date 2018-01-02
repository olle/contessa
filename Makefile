.PHONY: test start

test:
	@mvn clean verify

start:
	@mvn clean spring-boot:run
