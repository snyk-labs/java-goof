#!/usr/bin/env bash
MYDIR=$(dirname $0)
if [[ "$1" == "" ]]; then
  read -e -i "${DOCKER_ACCOUNT}" -p "Please enter your DockerHub user/account name: " input
  name="${input:-$DOCKER_ACCOUNT}"
else
  DOCKER_ACCOUNT=$1
fi

echo "📦 Building and pushing image ${DOCKER_ACCOUNT}/java-goof:latest ..."
docker buildx create --name mybuilder || true
docker buildx use mybuilder
docker buildx build --push --platform linux/amd64,linux/arm64 -t ${DOCKER_ACCOUNT}/java-goof:latest $MYDIR/..
