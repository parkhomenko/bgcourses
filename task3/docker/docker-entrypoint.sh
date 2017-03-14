#!/bin/bash

set -e

service ssh restart

is_master=true
for (( i=1; i<=${#}; i++));
do
  if [ "${!i}" == "-master" ]
  then
    is_master=false
    master_index=$(( $i + 1 ))
    master_host="hdfs://master:9000"
    xmlstarlet ed --inplace -u "/configuration/property/value[../name/text()='fs.defaultFS']" -v ${master_host} /usr/local/hadoop/etc/hadoop/core-site.xml
    break
  fi

  if [ "${!i}" == "-slave" ]
  then
    slave_index=$(( $i + 1 ))
    slave_host=${!slave_index}
    echo $slave_host >> /usr/local/hadoop/etc/hadoop/slaves
  fi
done

if [ $is_master = true ]
then
  master_host="hdfs://$(hostname):9000"
  xmlstarlet ed --inplace -u "/configuration/property/value[../name/text()='fs.defaultFS']" -v ${master_host} /usr/local/hadoop/etc/hadoop/core-site.xml
  /usr/local/hadoop/sbin/start-dfs.sh
  /usr/local/hadoop/sbin/start-yarn.sh
fi

while true; do sleep 1000; done
