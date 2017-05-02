package com.bgcourse.utils;

import com.bgcourse.entities.MovieRating;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class PopulationUtils {

  FileParser fileParser;
  Session session;

  public PopulationUtils() {
    Cluster cluster = Cluster.builder()
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

  public void addRecords() {
    for (int i = 0; i < 1; i++) {
      System.out.print(i + ": [");
      fileParser.collect(ratingRepository::addWithDummyDescription);
      System.out.println("]");
    }
  }

  public void addWithDummyDescription(MovieRating movieRating) {
    Session session = cassandraConnector.getSession();
    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    session.execute(query, uuidGenerator.getUUID(), uuidGenerator.getRandomString(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
  }
}
