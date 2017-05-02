package com.bgcourse.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bgcourse.entities.MovieRating;
import com.bgcourse.utils.UUIDGenerator;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;

import javax.annotation.PostConstruct;

@Repository
public class RatingRepository {

  @Autowired
  private CassandraConnector cassandraConnector;

  @Autowired
  private UUIDGenerator uuidGenerator;

  private BatchStatement batchStatement;
  private PreparedStatement preparedStatement;
  private Session session;

  @PostConstruct
  public void setUp() {
    this.batchStatement = new BatchStatement();

    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    this.session = cassandraConnector.getSession();
    preparedStatement = this.session.prepare(query);
  }

  public void add(MovieRating movieRating) {
    Session session = cassandraConnector.getSession();
    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    session.execute(query, uuidGenerator.getUUID(), movieRating.getDistribution(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
  }

  public void addWithDummyDescription(MovieRating movieRating) {
    Session session = cassandraConnector.getSession();
    String query = "insert into imdb.ratings(id, distribution, votes, rank, title) values(?, ?, ?, ?, ?)";
    session.execute(query, uuidGenerator.getUUID(), uuidGenerator.getRandomString(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
  }

  public void addBatch(MovieRating movieRating) {
    BoundStatement boundStatement = new BoundStatement(preparedStatement);
    boundStatement.bind(uuidGenerator.getUUID(), movieRating.getDistribution(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
    batchStatement.add(boundStatement);
    if (batchStatement.size() == 100) {
      session.execute(batchStatement);
      batchStatement.clear();
    }
  }

  public void addBatchOfUpdatingRecords(MovieRating movieRating) {
    BoundStatement boundStatement = new BoundStatement(preparedStatement);
    boundStatement.bind(uuidGenerator.getPredefinedUUID(), movieRating.getDistribution(), movieRating.getVotes(),
        movieRating.getRank(), movieRating.getTitle());
    batchStatement.add(boundStatement);
    if (batchStatement.size() == 100) {
      session.execute(batchStatement);
      batchStatement.clear();
    }
  }

  public List<MovieRating> getRatings(String prefix) {
    String query = "select * from imdb.ratings where expr(ratings_index, "
        + "'{filter: {type: \"prefix\", field: \"title\", value: \"" + prefix + "\"}}')";
    Statement statement = new SimpleStatement(query);
    ResultSet resultSet = session.execute(statement);

    List<MovieRating> movieRatings = new ArrayList<>();
    resultSet.forEach(e -> movieRatings.add(new MovieRating(
        e.getString("distribution"),
        e.getInt("votes"),
        e.getFloat("rank"),
        e.getString("title"))));

    return movieRatings;
  }

  public List<MovieRating> getRatingsByTitle(String title) {
    String query = "select * from imdb.ratings where expr(ratings_index, "
        + "'{query: {type: \"phrase\", field: \"title\", value: \"" + title + "\"}}')";
    Statement statement = new SimpleStatement(query);
    ResultSet resultSet = session.execute(statement);

    List<MovieRating> movieRatings = new ArrayList<>();
    resultSet.forEach(e -> movieRatings.add(new MovieRating(
        e.getString("distribution"),
        e.getInt("votes"),
        e.getFloat("rank"),
        e.getString("title"))));

    return movieRatings;
  }

  public void executeBatch() {
    session.execute(batchStatement);
  }
}
