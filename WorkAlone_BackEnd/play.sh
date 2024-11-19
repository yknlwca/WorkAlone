#!/bin/sh

./gradlew clean build -x test

docker rmi workalone_backend-workalone

docker compose up -d --build