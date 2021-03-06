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

# cassandra installation
RUN groupadd -r cassandra --gid=999 && \
    useradd -r -g cassandra --uid=999 cassandra

ENV GPG_KEYS \
    A278B781FE4B2BDA
RUN \
    for key in $GPG_KEYS; do \
      gpg --keyserver ha.pool.sks-keyservers.net --recv-keys "$key"; \
    done; \
    gpg --export $GPG_KEYS > /etc/apt/trusted.gpg.d/cassandra.gpg

RUN echo 'deb http://www.apache.org/dist/cassandra/debian 30x main' >> /etc/apt/sources.list.d/cassandra.list

ENV CASSANDRA_VERSION 3.0.12

RUN apt-get update && \
    apt-get install -y cassandra="$CASSANDRA_VERSION" && \
    apt-get install -y vim && \
    apt-get install -y netcat && \
    rm -rf /var/lib/apt/lists/*

ENV CASSANDRA_CONFIG /etc/cassandra

COPY ./libs/cassandra-lucene-index-plugin-3.0.10.4-RC1-SNAPSHOT.jar /usr/share/cassandra/lib/cassandra-lucene-index-plugin.jar
COPY ./docker-entrypoint.sh /docker-entrypoint.sh
ENTRYPOINT ["/docker-entrypoint.sh"]

RUN mkdir -p /var/lib/cassandra "$CASSANDRA_CONFIG" && \
    chown -R cassandra:cassandra /var/lib/cassandra "$CASSANDRA_CONFIG" && \
    chmod 777 /var/lib/cassandra "$CASSANDRA_CONFIG"
VOLUME /var/lib/cassandra

EXPOSE 7000 7001 7199 9042 9160
CMD ["cassandra", "-R", "-f"]
