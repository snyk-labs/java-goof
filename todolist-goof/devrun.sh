#!/bin/bash
SRC=$PWD/todolist-web-struts/target/todolist; kustomize build k8s/overlays/development | envsubst | kubectl apply -f -
