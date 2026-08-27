#!/bin/bash

SUITE=$1

export DISPLAY=:20
Xvfb :20 -screen 0 1366x768x16 &
mvn ${MAVEN_GOALS:-clean verify} -U -P${SUITE} -Dsurefire.skipAfterFailureCount=1
