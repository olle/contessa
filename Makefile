.PHONY: all test build clean

TARGET=contessa

all: test build

test:
	go test

build: ${TARGET} ${TARGET}.arm

${TARGET}: *.go
	go build -o $@

${TARGET}.arm: *.go
	env GOOS=linux GOARCH=arm GOARM=5 go build -o $@

clean:
	rm ${TARGET} ${TARGET}.arm

# ##
# ## Default targets for starting and debugging
# start:
# 	@docker-compose up -d
# 	@mvn clean spring-boot:run -Dcontessa.base-dir=.var/

# debug:
# 	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=.var/

# ##
# ## Does not require any docker resource
# ##
# start-file:
# 	@mvn clean spring-boot:run -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=file
# ##
# ## Custom targets supported:
# ##   - mariadb
# ##   - minio
# ##   - cassandra
# ##   - redis
# ##
# start-%:
# 	@docker-compose up -d $*
# 	@mvn clean spring-boot:run -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=$*

# debug-%:
# 	@docker-compose up -d $*
# 	@mvnDebug clean spring-boot:run -Ddebug=true -Dcontessa.base-dir=.var/ -Dspring-boot.run.profiles=$*
