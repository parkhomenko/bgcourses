# the file is based on ubuntu 16.04
FROM ubuntu:xenial

# oracle java 8 installation
RUN apt-get update && \
    apt-get install -y software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | \
         /usr/bin/debconf-set-selections && \
    apt-get install oracle-java8-installer -y && \
    apt-get install oracle-java8-set-default && \
    rm -rf /var/lib/apt/lists/*

RUN apt-get update && \
    apt-get install -y vim && \
    apt-get install -y wget && \
    apt-get install -y ssh && \
    apt-get install -y xmlstarlet && \
    apt-get install -y lsof && \
    rm -rf /var/lib/apt/lists/*

# passwordless ssh
RUN ssh-keygen -t rsa -P '' -f ~/.ssh/id_rsa && \
    cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys && \
    chmod 0600 ~/.ssh/authorized_keys

# hadoop installation
RUN wget http://mirrors.ae-online.de/apache/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz && \
    mkdir /usr/local/hadoop && \
    tar -xzvf hadoop-2.7.3.tar.gz && \
    mv hadoop-2.7.3/* /usr/local/hadoop

COPY ./core-site.xml /usr/local/hadoop/etc/hadoop/core-site.xml
COPY ./hdfs-site.xml /usr/local/hadoop/etc/hadoop/hdfs-site.xml

RUN sed -i "/export[ ]JAVA_HOME=/ s/=.*/=\/usr\/lib\/jvm\/java-8-oracle/" /usr/local/hadoop/etc/hadoop/hadoop-env.sh

RUN echo "StrictHostKeyChecking no" >> /etc/ssh/ssh_config && \
    echo "UserKnownHostsFile=/dev/null" >> /etc/ssh/ssh_config

RUN /usr/local/hadoop/bin/hadoop namenode -format

COPY ./docker-entrypoint.sh /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 50010 50020 50070 50075 50090
