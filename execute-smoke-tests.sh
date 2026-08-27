#!/bin/bash

SUITE=$1

export DISPLAY=:20
Xvfb :20 -screen 0 1366x768x16 &
# Intentionally unquoted: the default must word-split into two goals ("clean" "verify").
# Quoting this ("${MAVEN_GOALS:-clean verify}") would turn the default into a single
# invalid goal "clean verify" and break every caller that doesn't set MAVEN_GOALS (e.g.
# Bamboo). Set MAVEN_GOALS=verify when /smoketests/target is bind-mounted by the caller,
# to avoid mvn clean's EBUSY on rmdir-ing its own mount point.
mvn ${MAVEN_GOALS:-clean verify} -U -P${SUITE} -Dsurefire.skipAfterFailureCount=1
