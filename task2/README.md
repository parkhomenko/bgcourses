#Starting nodes in docker:

Slave:<br/>
docker run --name hdfs-slave -d hdfs -master 172.22.0.3

Master:<br/>
docker run --name hdfs-master -d -p 50070:50070 hdfs -slave 172.22.0.2
