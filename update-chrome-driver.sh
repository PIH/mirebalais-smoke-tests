#!/bin/bash

rm -fR src/test/resources/chromedriver/linux/*
wget -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_STABLE)/linux64/chromedriver-linux64.zip
unzip -j /tmp/chromedriver.zip 'chromedriver-linux64/chromedriver' -d src/test/resources/chromedriver/linux
