CREATE KEYSPACE imdb with replication =
  {'class': 'SimpleStrategy', 'replication_factor': 3};

CREATE TABLE imdb.ratings (
    id text PRIMARY KEY,
    distribution text,
    rank float,
    title text,
    votes int
);

CREATE CUSTOM INDEX ratings_index ON imdb.ratings ()
  USING 'com.stratio.cassandra.lucene.Index'
  WITH OPTIONS = {'refresh_seconds': '1', 'schema':
    '{fields :
      {id: {type: "string"},
      distribution : {type: "string"},
      votes: {type: "integer"},
      rank: {type: "float"},
      title: {type: "string"}}
    }'
  };
