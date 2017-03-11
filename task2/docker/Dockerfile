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

ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN apt-get update && \
    apt-get install -y vim && \
    apt-get install -y wget && \
    apt-get install -y openssh-server openssh-client rsync && \
    rm -rf /var/lib/apt/lists/*

# passwordless ssh
RUN echo "yes \n" | ssh-keygen -q -N "" -t dsa -f /etc/ssh/ssh_host_dsa_key
RUN echo "yes \n" | ssh-keygen -q -N "" -t rsa -f /etc/ssh/ssh_host_rsa_key
RUN echo "yes \n" | ssh-keygen -q -N "" -t rsa -f /root/.ssh/id_rsa
RUN cp /root/.ssh/id_rsa.pub /root/.ssh/authorized_keys

# hadoop installation
RUN wget http://mirrors.ae-online.de/apache/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz && \
    mkdir /usr/local/hadoop && \
    tar -xzvf hadoop-2.7.3.tar.gz && \
    mv hadoop-2.7.3/* /usr/local/hadoop

COPY ./core-site.xml /usr/local/hadoop/etc/hadoop/core-site.xml
COPY ./hdfs-site.xml /usr/local/hadoop/etc/hadoop/hdfs-site.xml

ADD ssh_config /root/.ssh/config
RUN chmod 600 /root/.ssh/config
RUN chown root:root /root/.ssh/config

COPY ./docker-entrypoint.sh /docker-entrypoint.sh
CMD ["/docker-entrypoint.sh", "-d"]

EXPOSE 50070
