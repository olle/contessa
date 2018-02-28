.PHONY: test start debug

test:
	@mvn clean verify

##
## Default targets for starting and debugging
start:
	@docker-compose up -d
	@mvn spring-boot:run -Dcontessa.base-dir=.var/

debug:
	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=.var/

##
## Does not require any docker resource
##
start-file:
	@mvn clean spring-boot:run -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=file
##
## Custom targets supported:
##   - mariadb
##   - minio
##   - cassandra
##   - redis
##
start-%:
	@docker-compose up -d $*
	@mvn clean spring-boot:run -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=$*

debug-%:
	@docker-compose up -d $*
	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=$*
