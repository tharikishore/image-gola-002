FROM ubuntu:14.04
MAINTAINER Harikishore Tadigotla <harikishore.t@gmail.com>

ENV TERM linux
ENV DEBIAN_FRONTEND noninteractive

RUN sudo apt-get update -y \
        && sudo apt-get install -y \
        software-properties-common \
        --no-install-recommends \
        && sudo apt-get install unzip -y \
        --no-install-recommends \
        && sudo add-apt-repository ppa:webupd8team/java -y \
        && sudo apt-get update -y \
        && echo debconf shared/accepted-oracle-license-v1-1 select true | \
        sudo debconf-set-selections \
        && echo debconf shared/accepted-oracle-license-v1-1 seen true | \
        sudo debconf-set-selections \
        && sudo apt-get install oracle-java8-installer -y \
        --no-install-recommends \
        && rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle
ENV JAVA_OPTS "-Xmx4096m -Xms1024m"

VOLUME /downloads

WORKDIR /downloads
RUN wget https://github.com/grails/grails-core/releases/download/v3.1.9/grails-3.1.9.zip \
        && unzip /downloads/grails-3.1.9.zip -d /programs \
        && sudo apt-get autoclean \
        && sudo apt-get clean \
        && rm -rf /downloads/*

RUN sudo apt-get autoremove

ENV GRAILS_HOME /programs/grails-3.1.9
ENV PATH $JAVA_HOME/bin:$GRAILS_HOME/bin:$PATH

VOLUME /root/.grails
VOLUME /root/.gradle

EXPOSE 8080
