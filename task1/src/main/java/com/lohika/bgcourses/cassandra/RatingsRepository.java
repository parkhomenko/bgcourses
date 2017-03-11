package com.lohika.bgcourses.cassandra;

import java.util.UUID;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.lohika.bgcourses.imdb.ratings.MovieRating;

public class RatingsRepository {

  private CassandraConnector cassandraConnector;
  private BatchStatement batchStatement;
  private PreparedStatement preparedStatement;
  private Session session;

  public RatingsRepository(CassandraConnector cassandraConnector) {
    this.cassandraConnector = cassandraConnector;
    this.batchStatement = new BatchStatement();

    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    this.session = cassandraConnector.getSession();
    preparedStatement = this.session.prepare(query);
  }

  public void add(MovieRating movieRating) {
    Session session = cassandraConnector.getSession();
    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    session.execute(query, getUUID(), movieRating.getDistribution(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
  }

  public void addBatch(MovieRating movieRating) {
    BoundStatement boundStatement = new BoundStatement(preparedStatement);
    boundStatement.bind(getUUID(), movieRating.getDistribution(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
    batchStatement.add(boundStatement);
    if (batchStatement.size() == 100) {
      session.execute(batchStatement);
      batchStatement.clear();
    }
  }

  public void executeBatch() {
    session.execute(batchStatement);
  }

  private String getUUID() {
    UUID uid = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
    return uid.randomUUID().toString();
  }
}
