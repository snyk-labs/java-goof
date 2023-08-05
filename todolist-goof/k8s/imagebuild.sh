#!/usr/bin/env bash
MYDIR=$(dirname $0)
if [[ "$1" == "" ]]; then
  read -e -i "${DOCKER_ACCOUNT}" -p "Please enter your DockerHub user/account name: " input
  name="${input:-$DOCKER_ACCOUNT}"
else
  DOCKER_ACCOUNT=$1
fi

echo "📦 Building and pushing image ${DOCKER_ACCOUNT}/java-goof:latest ..."
docker buildx create --name multiarch --use
PLATFORM=linux/amd64
if [[ $(uname -m) = arm64 ]]; then
  echo "Found arm64! Building arm64 and amd64 images..."
  PLATFORM=linux/arm64,linux/amd64
fi
docker buildx build --push --platform ${PLATFORM} -t ${DOCKER_ACCOUNT}/java-goof:latest $MYDIR/..
