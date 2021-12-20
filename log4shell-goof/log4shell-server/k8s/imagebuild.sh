#!/usr/bin/env bash
read -e -i "${DOCKER_ACCOUNT}" -p "Please enter your DockerHub user/account name: " input
name="${input:-$DOCKER_ACCOUNT}"

echo "📦 Building image ${DOCKER_ACCOUNT}/log4shell-server:latest ..."
docker build -t ${DOCKER_ACCOUNT}/log4shell-server:latest .
echo
echo "🚚 Pushing image to DockerHub..."
docker push ${DOCKER_ACCOUNT}/log4shell-server:latest
