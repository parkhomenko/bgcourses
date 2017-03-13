#Starting nodes in docker:

Slave:<br/>
docker run --name hdfs-slave -d hdfs -master 172.22.0.3

Master:<br/>
docker run --name hdfs-master -d -p 50070:50070 hdfs -slave 172.22.0.2

#Starting a cluster with docker compose:

Start:<br/>
docker build -t hdfs ./docker<br/>
docker-compose up -d

Stop:<br/>
docker-compose down --volumes

#Hdfs CLI:

Make a directory:<br/>
docker exec -it hdfs-master /usr/local/hadoop/bin/hadoop fs -mkdir -p /user/hadoop/ratings<br/>
docker exec -it hdfs-master /usr/local/hadoop/bin/hadoop fs -chmod +w /user/hadoop/ratings<br/>
docker exec -it hdfs-master /usr/local/hadoop/bin/hadoop fs -copyFromLocal /usr/local/files/ratings.list /user/hadoop/ratings<br/>
docker exec -it hdfs-master /usr/local/hadoop/bin/hadoop fs -copyToLocal /user/hadoop/ratings/ratings.list /usr/local/files

#Java tool for reading/writing to hdfs:

Build into one jar:<br/>
gradle fatJar

Usage:<br/>
java -jar *path to uber jar* *option* **local** **remote**

Examples:<br/>
java -jar ./services/build/libs/hdfs_util-1.0-SNAPSHOT.jar -write ./files/ratings.list /user/hadoop/ratings/ratings.list<br/>
java -jar ./services/build/libs/hdfs_util-1.0-SNAPSHOT.jar -read ./files/ratings.list /user/hadoop/ratings/ratings.list<br/>
java -jar ./services/build/libs/hdfs_util-1.0-SNAPSHOT.jar -help<br/>
