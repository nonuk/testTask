#!/usr/bin/env bash

# Local vars
C=false
be_dir=$(pwd)

# Read cmd line parameters
while getopts ":c" o
do case "$o" in
   c) C=true;;
   \?) echo "
Usage: $0 [-c]
   -c Recompile project
" >&2
      exit 1;;
   esac
done

if [[ ${C} == true ]]; then
   echo "Recompiling the project"
   mvn clean install -DskipTests=true
fi

if [[ $? == 0 ]]; then
pid=""
set -e
function cleanup {
   echo "Killing tomcat..."
   kill -9 $pid
}

    trap cleanup EXIT

    echo "Starting the server with spring-boot"
    mvn spring-boot:run > out.log & pid=$!
    echo ${pid}
    tail -f out.log
    cleanup
fi