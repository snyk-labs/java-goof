#!/usr/bin/env bash
TOP_LEVEL_MYDIR=$(dirname $0)
$TOP_LEVEL_MYDIR/todolist-goof/k8s/shutdown.sh
$TOP_LEVEL_MYDIR/log4shell-goof/log4shell-server/k8s/shutdown.sh
