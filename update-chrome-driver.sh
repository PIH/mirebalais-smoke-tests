#!/bin/bash

rm -fR src/test/resources/chromedriver/linux/*
wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/$(wget -qO- https://chromedriver.storage.googleapis.com/LATEST_RELEASE)/chromedriver_linux64.zip
unzip /tmp/chromedriver.zip -d src/test/resources/chromedriver/linux
