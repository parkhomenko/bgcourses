# Starting a cluster

Start:<br/>
docker build -t yarn ./docker<br/>
docker-compose up -d

Stop:<br/>
docker-compose down --volumes

# Map-reduce job

Build into one jar:<br/>
gradle uberJar

Copy a file into hdfs:<br/>
/usr/local/hadoop/bin/hadoop fs -copyFromLocal /usr/local/files/input.txt /usr/hadoop/store

Start a job:<br/>
/usr/local/hadoop/bin/hadoop jar /usr/local/files/store-1.0-SNAPSHOT.jar /usr/hadoop/store/input.txt /usr/hadoop/store/output
