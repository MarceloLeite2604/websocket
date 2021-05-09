.PHONY: frontend backend

install-frontend:
	@if [ ! -d frontend/node_modules ];\
	then\
		yarn --cwd frontend install;\
	fi;

clean-frontend: install-frontend
	@yarn --cwd frontend clean

build-frontend: install-frontend
	@yarn --cwd frontend build

clean-backend:
	@cd backend && ./mvnw clean

build-backend:
	@cd backend && ./mvnw package

docker-compose: build-frontend build-backend
	@docker-compose build --force-rm --no-cache
	@docker-compose up --force-recreate

clean: clean-frontend clean-backend

all: docker-compose