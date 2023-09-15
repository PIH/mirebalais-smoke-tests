FROM ubuntu:latest

MAINTAINER Michael Seaton <mseaton@pih.org>

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y unzip software-properties-common curl wget xvfb openjdk-11-jdk maven libxkbcommon0 libgbm-dev

RUN wget -O /tmp/chrome.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_STABLE)/linux64/chrome-linux64.zip
RUN unzip /tmp/chrome.zip -d /opt
RUN ln -s /opt/chrome-linux64/chrome /usr/bin/google-chrome
RUN ln -s /opt/chrome-linux64/chrome /usr/bin/google-chrome-stable

ADD . /smoketests

RUN rm -fR /smoketests/src/test/resources/chromedriver/linux/*
RUN wget -O /tmp/chromedriver.zip https://edgedl.me.gvt1.com/edgedl/chrome/chrome-for-testing/$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/LATEST_RELEASE_STABLE)/linux64/chromedriver-linux64.zip
RUN unzip -j /tmp/chromedriver.zip 'chromedriver-linux64/chromedriver' -d /smoketests/src/test/resources/chromedriver/linux

WORKDIR /smoketests

CMD /bin/bash



