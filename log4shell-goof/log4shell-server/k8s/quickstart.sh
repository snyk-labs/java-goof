#!/usr/bin/env bash
LOG4SHELL_MYDIR=$(dirname $0)
if [[ "$1" == "" ]]; then
  read -e -i "${DOCKER_ACCOUNT}" -p "Please enter your DockerHub user/account name: " input
  name="${input:-$DOCKER_ACCOUNT}"
else
  DOCKER_ACCOUNT=$1
fi

$LOG4SHELL_MYDIR/imagebuild.sh $DOCKER_ACCOUNT
$LOG4SHELL_MYDIR/startup.sh $DOCKER_ACCOUNT


