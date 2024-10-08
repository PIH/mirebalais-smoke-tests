FROM ubuntu:latest

MAINTAINER Michael Seaton <mseaton@pih.org>

ARG DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y unzip software-properties-common curl wget xvfb openjdk-11-jdk maven

RUN wget -O /tmp/chrome.deb https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
RUN apt-get install -y /tmp/chrome.deb
RUN echo $(/usr/bin/google-chrome --version) | cut -d ' ' -f 3 > /tmp/chromeversion

ADD . /smoketests

RUN rm -fR /smoketests/src/test/resources/chromedriver/linux/*

RUN wget -O /tmp/chromedriver.zip https://storage.googleapis.com/chrome-for-testing-public/$(cat /tmp/chromeversion)/linux64/chromedriver-linux64.zip
RUN unzip -j /tmp/chromedriver.zip 'chromedriver-linux64/chromedriver' -d /smoketests/src/test/resources/chromedriver/linux

WORKDIR /smoketests

CMD /bin/bash