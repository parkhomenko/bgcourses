package com.bgcourse.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bgcourse.settings.CassandraSettings;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
public class CassandraConnector {

  private Cluster cluster;
  private Session session;

  @Autowired
  private CassandraSettings cassandraSettings;

  @PostConstruct
  public void setUp() {
    this.cluster = Cluster.builder()
        .addContactPoint(cassandraSettings.getHost())
        .withPort(cassandraSettings.getPort())
        .build();

    final Metadata metadata = cluster.getMetadata();
    System.out.printf("Connected to cluster: %s\n", metadata.getClusterName());
    for (final Host host : metadata.getAllHosts()) {
      System.out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
          host.getDatacenter(), host.getAddress(), host.getRack());
    }
    session = cluster.connect();
  }

  public Session getSession() {
    return this.session;
  }

  @PreDestroy
  public void cleanUp() {
    cluster.close();
  }
}
