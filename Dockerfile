FROM ubuntu:latest

MAINTAINER Michael Seaton <mseaton@pih.org>

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y unzip software-properties-common curl wget xvfb openjdk-11-jdk maven

RUN wget -O /tmp/chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y /tmp/chrome.deb

ADD . /smoketests

RUN rm -fR /smoketests/src/test/resources/chromedriver/linux/*
RUN wget -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_116)/linux64/chromedriver-linux64.zip
# try switching back to this after next chrome release, if that doesn't work can run dpkg --info chrome.deb on the above deb to get the version and substitute that for the "_116" above to download the right chromedriver
#RUN wget -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_STABLE)/linux64/chromedriver-linux64.zip
RUN unzip -j /tmp/chromedriver.zip 'chromedriver-linux64/chromedriver' -d /smoketests/src/test/resources/chromedriver/linux

WORKDIR /smoketests

CMD /bin/bash


