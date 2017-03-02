.PHONY: test start

test:
	@./mvnw clean verify

start:
	@./mvnw clean spring-boot:run
