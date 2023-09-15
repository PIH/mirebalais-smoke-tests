#!/bin/bash

SUITE=$1

export DISPLAY=:20
Xvfb :20 -screen 0 1366x768x16 &
mvn clean verify -U -X -P${SUITE} -Dsurefire.skipAfterFailureCount=1
